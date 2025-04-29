package io.github.haeun.coupon.service;

import io.github.haeun.coupon.common.constant.CouponConstants;
import io.github.haeun.coupon.common.exception.CustomException;
import io.github.haeun.coupon.common.exception.ErrorCode;
import io.github.haeun.coupon.domain.coupons.Coupons;
import io.github.haeun.coupon.domain.coupons.CouponsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class CouponService {
    private final RedisTemplate<String, String> redisTemplate;
    private final CouponsRepository couponsRepository;

    public boolean issueCoupon(Long couponId) {
        if (couponId == null) {
            log.error("couponId is null");
            throw new CustomException(ErrorCode.INVALID_REQUEST);
        }

        String key = CouponConstants.getCouponStockKey(couponId);

        // Key 존재 여부 확인 후 DB 조회
        boolean exist = redisTemplate.hasKey(key);
        if (!exist) {
            Coupons coupons = couponsRepository.findById(couponId).orElse(null);
            if (coupons == null) {
                redisTemplate.opsForValue().set(key, CouponConstants.VALUE_NULL, Duration.ofHours(1));
                log.info("RedisUpdated couponId: {}, totalQuantity: {}", couponId, CouponConstants.VALUE_NULL);
                throw new CustomException(ErrorCode.COUPON_NOT_FOUND);
            }
            redisTemplate.opsForValue().set(key, String.valueOf(coupons.getTotalQuantity()), Duration.ofHours(1));
            log.info("RedisUpdated couponId: {}, totalQuantity: {}", couponId, coupons.getTotalQuantity());
        }

        Long stock = redisTemplate.opsForValue().decrement(key);
        if (stock < 0) {
            // 재고가 0 미만이면 1 증가시켜 원복하기 (0으로)
            redisTemplate.opsForValue().increment(key);
            log.error("Out Of Stock couponId:{} ", couponId);
            throw new CustomException(ErrorCode.COUPON_OUT_OF_STOCK);
        }

        // 발급 성공 Redis Stream에 기록
        Map<String, String> message = new HashMap<>();
        message.put("couponId", String.valueOf(couponId));
        message.put("userId", "testUser");
        redisTemplate.opsForStream().add(CouponConstants.STREAM_KEY, message);

        return true;
    }
}

package io.github.haeun.coupon.service;

import io.github.haeun.coupon.common.constant.CouponConstants;
import io.github.haeun.coupon.common.exception.CustomException;
import io.github.haeun.coupon.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CouponService {
    private final RedisTemplate<String, String> redisTemplate;

    public boolean issueCoupon(Long couponId) {
        if (couponId == null) {
            log.error("couponId is null");
            throw new CustomException(ErrorCode.INVALID_REQUEST);
        }

        String key = CouponConstants.getCouponStockKey(couponId);
        Long stock = redisTemplate.opsForValue().decrement(key);

        if (stock < 0) {
            // 재고가 0 미만이면 1 증가시켜 원복하기 (0으로)
            redisTemplate.opsForValue().increment(key);
            log.error("Out Of Stock couponId:{} ", couponId);
            throw new CustomException(ErrorCode.COUPON_OUT_OF_STOCK);
        }

        return true;
    }
}

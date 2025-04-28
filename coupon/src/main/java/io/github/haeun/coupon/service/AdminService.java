package io.github.haeun.coupon.service;

import io.github.haeun.coupon.common.exception.CustomException;
import io.github.haeun.coupon.common.exception.ErrorCode;
import io.github.haeun.coupon.domain.coupons.Coupons;
import io.github.haeun.coupon.domain.coupons.CouponsRepository;
import io.github.haeun.coupon.web.dto.CreateCouponRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminService {
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String COUPON_STOCK_PREFIX = "coupon:";
    private final CouponsRepository couponsRepository;

    /**
     * 쿠폰을 DB와 Redis에 추가하는 메서드
     *
     * @param request
     * - name: 쿠폰명
     * - totalQuantity: 쿠폰 개수
     * - ttlSeconds: 만료 시간
     * @return 쿠폰 아이디 (예외 발생 시 null)
     */
    @Transactional
    public Long createCoupon(CreateCouponRequest request) {
        try {
            // 1. DB에 쿠폰 저장
            Coupons coupon = Coupons.builder()
                    .name(request.getName())
                    .totalQuantity(request.getTotalQuantity())
                    .build();
            Coupons savedCoupon = couponsRepository.save(coupon);

            // 2. Redis에 재고 저장
            String key = COUPON_STOCK_PREFIX + savedCoupon.getId() + ":stock";
            if (request.getTtlSeconds() != null && request.getTtlSeconds() > 0) {
                redisTemplate.opsForValue().set(key, request.getTotalQuantity(), Duration.ofSeconds(request.getTtlSeconds()));
            } else {
                redisTemplate.opsForValue().set(key, request.getTotalQuantity());
            }
            return savedCoupon.getId();
        } catch (Exception e) {
            log.error("{} ERROR!", request.toString(), e);
            throw new CustomException(ErrorCode.COUPON_CREATION_FAILED);
        }
    }


    /**
     * 쿠폰 재고 수량을 Redis에서 조회하는 메서드
     *
     * @param couponId 쿠폰 ID
     * @return 남은 수량 (없으면 null)
     */
    public Integer getCouponStock(Long couponId) {
        if (couponId == null) {
            throw new CustomException(ErrorCode.INVALID_REQUEST);
        }

        String key = COUPON_STOCK_PREFIX + couponId + ":stock";
        Object stockObj = redisTemplate.opsForValue().get(key);

        if (stockObj == null) {
            throw new CustomException(ErrorCode.COUPON_NOT_FOUND);
        }

        try {
            return (Integer) stockObj;
        } catch (ClassCastException e) {
            log.error("couponId: {} ERROR!", couponId, e);
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

}

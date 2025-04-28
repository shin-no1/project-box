package io.github.haeun.coupon.service;

import io.github.haeun.coupon.web.dto.CouponStockRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class CouponService {
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String COUPON_STOCK_PREFIX = "coupon:";

    /**
     * 쿠폰 재고를 Redis에 초기화하는 메서드
     *
     * @param request
     * - couponId 쿠폰 ID
     * - quantity 초기 재고 수량
     * - ttlSeconds TTL (초 단위, 선택값. null이면 무제한 저장)
     */
    public void initCouponStock(CouponStockRequest request) {
        if (request.getCouponId() == null || request.getQuantity() <= 0) {
            // TODO: 에러 예외처리
            throw new IllegalArgumentException("couponId와 quantity는 필수이며, quantity는 1 이상이어야 합니다.");
        }

        String key = COUPON_STOCK_PREFIX + request.getCouponId() + ":stock";

        try {
            if (request.getTtlSeconds() > 0) {
                redisTemplate.opsForValue().set(key, request.getQuantity(), Duration.ofSeconds(request.getTtlSeconds()));
            } else {
                redisTemplate.opsForValue().set(key, request.getQuantity());
            }
        } catch (Exception e) {
            // TODO: 에러 예외처리
            throw new RuntimeException("쿠폰 초기화 중 Redis 오류 발생", e);
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
            // TODO: 예외처리
            throw new IllegalArgumentException("couponId는 필수입니다.");
        }

        String key = COUPON_STOCK_PREFIX + couponId + ":stock";
        Object stockObj = redisTemplate.opsForValue().get(key);

        if (stockObj == null) {
            return null; // 존재하지 않음
        }
        return (Integer) stockObj;
    }

}

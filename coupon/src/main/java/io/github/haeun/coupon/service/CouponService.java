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

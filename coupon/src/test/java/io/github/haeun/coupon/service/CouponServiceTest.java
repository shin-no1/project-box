package io.github.haeun.coupon.service;

import io.github.haeun.coupon.web.dto.CouponStockRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class CouponServiceTest {
    @Autowired
    private CouponService couponService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String COUPON_STOCK_PREFIX = "coupon:";

    void cleanUp(String key) {
        redisTemplate.delete(key);
    }

    @Test
    @DisplayName("쿠폰 재고 초기화 - TTL 없이 저장")
    void initCouponStock_withoutTTL_success() {
        Long couponId = Long.MAX_VALUE - 1;
        int quantity = 100;
        int ttlSeconds = 0;
        String key = COUPON_STOCK_PREFIX + couponId + ":stock";

        couponService.initCouponStock(new CouponStockRequest(couponId, quantity, ttlSeconds));

        Object stock = redisTemplate.opsForValue().get(key);
        assertThat(stock).isEqualTo(quantity);

        cleanUp(key);
    }

    @Test
    @DisplayName("쿠폰 재고 초기화 - TTL 설정해서 저장")
    void initCouponStock_withTTL_success() {
        Long couponId = 456L;
        int quantity = 200;
        int ttlSeconds = 60;
        String key = COUPON_STOCK_PREFIX + couponId + ":stock";

        couponService.initCouponStock(new CouponStockRequest(couponId, quantity, ttlSeconds));

        Object stock = redisTemplate.opsForValue().get(key);
        assertThat(stock).isEqualTo(quantity);

        // TTL이 0보다 큰지 확인 (만료 시간이 걸렸는지 체크)
        Long ttl = redisTemplate.getExpire(key);
        assertThat(ttl).isGreaterThan(0);
    }

    @Test
    @DisplayName("쿠폰 재고 초기화 - 필수 파라미터 누락시 예외 발생")
    void initCouponStock_missingParameters_fail() {
        Long couponId = null;
        int quantity = 0;
        int ttlSeconds = 0;

        assertThrows(IllegalArgumentException.class, () -> {
            couponService.initCouponStock(new CouponStockRequest(couponId, quantity, ttlSeconds));
        });
    }

}

package io.github.haeun.coupon.service;

import io.github.haeun.coupon.domain.coupons.Coupons;
import io.github.haeun.coupon.domain.coupons.CouponsRepository;
import io.github.haeun.coupon.web.dto.CreateCouponRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class AdminServiceTest {
    @Autowired
    private AdminService adminService;
    @Autowired
    private CouponsRepository couponsRepository;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${spring.test-mode:false}")
    private boolean testMode;

    private static final String COUPON_STOCK_PREFIX = "coupon:";

    @Test
    @DisplayName("쿠폰 등록 및 Redis 재고 초기화 성공")
    void createCoupon_success() {
        if (!testMode) {
            return;
        }
        // given
        CreateCouponRequest request = new CreateCouponRequest("테스트 쿠폰", 100, 3600L);

        // when
        Long couponId = adminService.createCoupon(request);

        // then
        // DB에 저장되었는지 확인
        Coupons coupon = couponsRepository.findById(couponId).orElse(null);
        assertThat(coupon).isNotNull();
        assertThat(coupon.getName()).isEqualTo("테스트 쿠폰");
        assertThat(coupon.getTotalQuantity()).isEqualTo(100);

        // Redis에 재고 등록되었는지 확인
        String key = COUPON_STOCK_PREFIX + couponId + ":stock";
        Object stock = redisTemplate.opsForValue().get(key);
        assertThat(stock).isEqualTo(100);

        // TTL 설정되었는지 확인
        Long ttl = redisTemplate.getExpire(key);
        assertThat(ttl).isGreaterThan(0);
    }
}

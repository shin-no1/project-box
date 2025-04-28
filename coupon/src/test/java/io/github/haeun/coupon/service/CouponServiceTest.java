package io.github.haeun.coupon.service;

import io.github.haeun.coupon.common.constant.CouponConstants;
import io.github.haeun.coupon.common.exception.CustomException;
import io.github.haeun.coupon.common.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
public class CouponServiceTest {
    @Autowired
    private CouponService couponService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final Long COUPON_ID = Long.MAX_VALUE;
    private static final String STOCK_KEY = CouponConstants.getCouponStockKey(COUPON_ID);

    @BeforeEach
    void setUp() {
        // 매 테스트 전에 쿠폰 재고를 10개로 초기화
        redisTemplate.opsForValue().set(STOCK_KEY, "10");
    }

    @Nested
    @DisplayName("쿠폰 발급(issueCoupon) 통합 테스트")
    class IssueCouponTest {

        @Test
        @DisplayName("정상적으로 쿠폰 발급에 성공한다")
        void issueCoupon_success() {
            // when
            boolean result = couponService.issueCoupon(COUPON_ID);

            // then
            assertThat(result).isTrue();

            // 재고가 9로 감소했는지 확인
            String stock = redisTemplate.opsForValue().get(STOCK_KEY);
            assertThat(stock).isEqualTo("9");
        }

        @Test
        @DisplayName("쿠폰 재고가 0 이하일 경우 발급에 실패한다")
        void issueCoupon_outOfStock() {
            // 재고를 0으로 세팅
            redisTemplate.opsForValue().set(STOCK_KEY, "0");

            // when / then
            assertThatThrownBy(() -> couponService.issueCoupon(COUPON_ID))
                    .isInstanceOf(CustomException.class)
                    .hasMessageContaining(ErrorCode.COUPON_OUT_OF_STOCK.getMessage());

            // 재고는 다시 0으로 복구돼야 함
            String stock = redisTemplate.opsForValue().get(STOCK_KEY);
            assertThat(stock).isEqualTo("0");
        }

        @Test
        @DisplayName("couponId가 null일 경우 INVALID_REQUEST 예외 발생")
        void issueCoupon_invalidRequest() {
            assertThatThrownBy(() -> couponService.issueCoupon(null))
                    .isInstanceOf(CustomException.class)
                    .hasMessageContaining(ErrorCode.INVALID_REQUEST.getMessage());
        }
    }
}

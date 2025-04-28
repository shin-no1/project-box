package io.github.haeun.coupon.service;

import io.github.haeun.coupon.domain.coupons.Coupons;
import io.github.haeun.coupon.domain.coupons.CouponsRepository;
import io.github.haeun.coupon.web.dto.CreateCouponRequest;
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
        }
        return null;
    }

}

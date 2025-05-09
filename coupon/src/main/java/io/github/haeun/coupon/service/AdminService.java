package io.github.haeun.coupon.service;

import io.github.haeun.coupon.common.constant.CouponConstants;
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
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminService {
    private final RedisTemplate<String, String> redisTemplate;
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
            String key = CouponConstants.getCouponStockKey(savedCoupon.getId());
            if (request.getTtlSeconds() != null && request.getTtlSeconds() > 0) {
                redisTemplate.opsForValue().set(key, String.valueOf(request.getTotalQuantity()), Duration.ofSeconds(request.getTtlSeconds()));
            } else {
                redisTemplate.opsForValue().set(key, String.valueOf(request.getTotalQuantity()));
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
            log.error("couponId is null");
            throw new CustomException(ErrorCode.INVALID_REQUEST);
        }

        String key = CouponConstants.getCouponStockKey(couponId);
        String stockObj = redisTemplate.opsForValue().get(key);

        if (stockObj == null) { // TODO: 잔여 stock 으로 변경해야할듯
            // Redis에 없으면 DB 조회
            Coupons coupon = couponsRepository.findById(couponId).orElse(null);

            if (coupon == null) { // 존재하지 않는 쿠폰은 NULL 로 저장
                redisTemplate.opsForValue().set(key, CouponConstants.VALUE_NULL, Duration.ofMinutes(1));
                throw new CustomException(ErrorCode.COUPON_NOT_FOUND);
            }

            // DB에 있으면 Redis 적재
            redisTemplate.opsForValue().set(key, String.valueOf(coupon.getTotalQuantity()), Duration.ofHours(6));
            return coupon.getTotalQuantity();
        }

        if (CouponConstants.VALUE_NULL.equals(stockObj)) {
            log.error("couponId {} is null", couponId);
            throw new CustomException(ErrorCode.COUPON_NOT_FOUND);
        }

        try {
            return Integer.parseInt(stockObj);
        } catch (ClassCastException e) {
            log.error("couponId: {} ERROR!", couponId, e);
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Redis 쿠폰 초기화
     */
    public void initializeCouponStocks() {
        List<Coupons> allCoupons = couponsRepository.findAll();
        for (Coupons coupon : allCoupons) {
            String key = CouponConstants.getCouponStockKey(coupon.getId());
            redisTemplate.opsForValue().set(key, String.valueOf(coupon.getTotalQuantity() - coupon.getIssuedQuantity()));
        }
    }

}

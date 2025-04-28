package io.github.haeun.coupon.web.dto;

import lombok.Getter;

@Getter
public class CouponStockRequest {
    private final Long couponId;
    private final int quantity;
    private final long ttlSeconds;

    public CouponStockRequest(Long couponId, int quantity, long ttlSeconds) {
        this.couponId = couponId;
        this.quantity = quantity;
        this.ttlSeconds = ttlSeconds;
    }
}

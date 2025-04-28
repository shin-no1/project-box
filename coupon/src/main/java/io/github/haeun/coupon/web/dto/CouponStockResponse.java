package io.github.haeun.coupon.web.dto;

import lombok.Getter;

@Getter
public class CouponStockResponse {
    private final Long couponId;
    private final Integer stock;

    public CouponStockResponse(Long couponId, Integer stock) {
        this.couponId = couponId;
        this.stock = stock;
    }
}
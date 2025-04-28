package io.github.haeun.coupon.web.dto;

import lombok.Getter;

@Getter
public class CreateCouponResponse {
    private final Long couponId;
    private final String message;

    public CreateCouponResponse(Long couponId, String message) {
        this.couponId = couponId;
        this.message = message;
    }
}
package io.github.haeun.coupon.web.dto;

import lombok.Getter;

@Getter
public class CreateCouponRequest {
    private final String name;
    private final int totalQuantity;
    private final Long ttlSeconds;

    public CreateCouponRequest(String name, int totalQuantity, Long ttlSeconds) {
        this.name = name;
        this.totalQuantity = totalQuantity;
        this.ttlSeconds = ttlSeconds;
    }

    @Override
    public String toString() {
        return "CreateCouponRequest [name=" + name + ", totalQuantity=" + totalQuantity + ", ttlSeconds=" + ttlSeconds + "]";
    }
}

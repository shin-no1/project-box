package io.github.haeun.coupon.common.constant;

public final class CouponConstants {
    public static final String VALUE_NULL = "NULL";
    public static final String COUPON_STOCK_PREFIX = "coupon:";

    public static String getCouponStockKey(Long couponId) {
        return COUPON_STOCK_PREFIX + couponId + ":stock";
    }
}

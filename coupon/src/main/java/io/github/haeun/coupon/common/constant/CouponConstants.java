package io.github.haeun.coupon.common.constant;

public final class CouponConstants {
    public static final String VALUE_NULL = "-999";
    public static final String COUPON_STOCK_PREFIX = "coupon:";
    public static final String STREAM_KEY = "coupon_issue_stream";
    public static final String GROUP_NAME = "coupon_issue_group";
    public static final String CONSUMER_NAME = "coupon_issue_consumer_1";

    public static String getCouponStockKey(Long couponId) {
        return COUPON_STOCK_PREFIX + couponId + ":stock";
    }
}

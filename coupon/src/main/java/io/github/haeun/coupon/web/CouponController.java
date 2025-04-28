package io.github.haeun.coupon.web;

import io.github.haeun.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/coupons")
@RestController
public class CouponController {
    private final CouponService couponService;

    @PostMapping("/{couponId}/issue")
    public ResponseEntity<String> issueCoupon(@PathVariable Long couponId) {
        couponService.issueCoupon(couponId);
        return ResponseEntity.ok("쿠폰 발급 성공");
    }
}

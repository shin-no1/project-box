package io.github.haeun.coupon.web;

import io.github.haeun.coupon.service.CouponService;
import io.github.haeun.coupon.web.dto.IssueCouponRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/coupons")
@RestController
public class CouponController {
    private final CouponService couponService;

    @PostMapping("/{couponId}/issue")
    public ResponseEntity<String> issueCoupon(@PathVariable Long couponId, @RequestBody IssueCouponRequest request) {
        couponService.issueCoupon(couponId, request.getUserId());
        return ResponseEntity.ok("쿠폰 발급 성공");
    }
}

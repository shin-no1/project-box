package io.github.haeun.coupon.web;

import io.github.haeun.coupon.service.CouponService;
import io.github.haeun.coupon.web.dto.CouponStockResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/coupons")
@RestController
public class CouponController {
    private final CouponService couponService;

    @PostMapping("/issue")
    public ResponseEntity<String> issueCoupon(@RequestParam Long couponId) {
        if (couponService.issueCoupon(couponId)) {
            return ResponseEntity.ok("쿠폰 발급 성공");
        } else {
            return ResponseEntity.badRequest().body("쿠폰 소진");
        }
    }
}

package io.github.haeun.coupon.web;

import io.github.haeun.coupon.service.CouponService;
import io.github.haeun.coupon.web.dto.CouponStockRequest;
import io.github.haeun.coupon.web.dto.CouponStockResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/coupons")
@RestController
public class CouponController {
    private final CouponService couponService;

    @GetMapping("/{couponId}")
    public ResponseEntity<?> getCouponStock(@PathVariable Long couponId) {
        Integer stock = couponService.getCouponStock(couponId);

        if (stock == null) {
            return ResponseEntity.status(404).body("쿠폰을 찾을 수 없습니다.");
        }

        return ResponseEntity.ok(new CouponStockResponse(couponId, stock));
    }

}

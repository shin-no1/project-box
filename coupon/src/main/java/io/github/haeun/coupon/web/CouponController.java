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

    @PostMapping("/init")
    public ResponseEntity<String> initCouponStock(CouponStockRequest request) {
        try {
            couponService.initCouponStock(request);
            return ResponseEntity.ok("쿠폰 초기화 완료");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("에러 발생");
        }
    }
}

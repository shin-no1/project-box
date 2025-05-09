package io.github.haeun.coupon.web;

import io.github.haeun.coupon.service.AdminService;
import io.github.haeun.coupon.web.dto.CouponStockResponse;
import io.github.haeun.coupon.web.dto.CreateCouponRequest;
import io.github.haeun.coupon.web.dto.CreateCouponResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/admin")
@RequiredArgsConstructor
@RestController
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/coupons")
    public ResponseEntity<?> createCoupon(@RequestBody CreateCouponRequest request) {
        Long couponId = adminService.createCoupon(request);
        return ResponseEntity.ok(new CreateCouponResponse(couponId, "쿠폰 등록 및 재고 초기화 완료"));
    }

    @GetMapping("/coupons/{couponId}")
    public ResponseEntity<?> getCoupon(@PathVariable Long couponId) {
        Integer stock = adminService.getCouponStock(couponId);
        return ResponseEntity.ok(new CouponStockResponse(couponId, stock));
    }
}

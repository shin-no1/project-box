package io.github.haeun.coupon.service;

import io.github.haeun.coupon.domain.couponIssues.CouponIssues;
import io.github.haeun.coupon.domain.couponIssues.CouponIssuesRepository;
import io.github.haeun.coupon.domain.coupons.CouponsRepository;
import io.github.haeun.coupon.domain.jdbc.CouponBatchRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CouponIssueTransactionalService {
    private final CouponIssuesRepository couponIssuesRepository;
    private final CouponsRepository couponsRepository;
    private final CouponBatchRepository couponBatchRepository;

    @Transactional
    public void saveIssueAndIncrementCount(Long couponId, List<CouponIssues> issues) {
//        couponIssuesRepository.saveAll(issues);
        couponBatchRepository.batchInsertCouponIssues(issues);
        couponsRepository.incrementIssuedQuantityByCount(couponId, issues.size());
        log.info("Save coupon issued couponId: {}, count: {}", couponId, issues.size());
    }
}

package io.github.haeun.coupon.domain.couponIssues;

import io.github.haeun.coupon.domain.coupons.Coupons;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Entity
public class CouponIssues {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50, nullable = false)
    private String userId;

    @ManyToOne()
    @JoinColumn(name = "coupon_id", nullable = false)
    private Coupons coupons;

    @CreationTimestamp
    private Timestamp createdAt;

    @Builder
    public CouponIssues(String userId, Coupons coupons) {
        this.userId = userId;
        this.coupons = coupons;
    }
}

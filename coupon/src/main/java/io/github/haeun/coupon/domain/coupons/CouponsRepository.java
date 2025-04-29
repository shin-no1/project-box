package io.github.haeun.coupon.domain.coupons;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CouponsRepository extends JpaRepository<Coupons, Long> {
    @Modifying
    @Query("UPDATE Coupons c SET c.issuedQuantity = c.issuedQuantity + :count, c.updatedAt = CURRENT_TIMESTAMP WHERE c.id = :couponId")
    void incrementIssuedQuantityByCount(@Param("couponId") Long couponId, @Param("count") int count);
}

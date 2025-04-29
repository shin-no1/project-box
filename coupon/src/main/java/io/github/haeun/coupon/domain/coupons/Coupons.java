package io.github.haeun.coupon.domain.coupons;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor

@Getter
@Entity
public class Coupons {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 50, nullable = false)
    private String name;

    @ColumnDefault("0")
    @Column(nullable = false)
    private int totalQuantity;

    @ColumnDefault("0")
    @Column(nullable = false)
    private int issuedQuantity;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    @Builder
    public Coupons(Long id, String name, int totalQuantity) {
        if (id != null) this.id = id;
        this.name = name;
        this.totalQuantity = totalQuantity;
    }
}

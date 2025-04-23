package io.github.haeun.carbon_scope.domain.region;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer regionCode;

    @Column(length = 50, nullable = false)
    private String name;

    @Builder
    public Region(Integer regionCode, String name) {
        this.regionCode = regionCode;
        this.name = name;
    }

}

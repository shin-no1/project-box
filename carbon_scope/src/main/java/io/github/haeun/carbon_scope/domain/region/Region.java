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

    @Column(length = 20, nullable = false)
    private String depth1;

    @Column(length = 20, nullable = false)
    private String depth2;
}

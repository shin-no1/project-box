package io.github.haeun.petstats.domain.region;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Region {
    @Id
    private Integer id;

    @Column(length = 10, nullable = false)
    private String province;

    @Column(length = 10, nullable = false)
    private String city;
}

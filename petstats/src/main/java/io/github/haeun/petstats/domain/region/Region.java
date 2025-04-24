package io.github.haeun.petstats.domain.region;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "multiUniqueConstraint", columnNames = {
                "province", "city"
        })
})
@Entity
public class Region {
    @Id
    private Integer id;

    @Column(length = 10, nullable = false)
    private String province;

    @Column(length = 10, nullable = false)
    private String city;
}

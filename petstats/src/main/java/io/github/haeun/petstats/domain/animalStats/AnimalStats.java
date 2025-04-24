package io.github.haeun.petstats.domain.animalStats;

import io.github.haeun.petstats.domain.animalType.AnimalType;
import io.github.haeun.petstats.domain.region.Region;
import io.github.haeun.petstats.domain.rfidType.RfidType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "multiUniqueConstraint", columnNames = {
                "region_id", "animal_type_id", "birthYear", "rfid_type_id"
        })
})
@Entity
public class AnimalStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    @ManyToOne
    @JoinColumn(name = "animal_type_id", nullable = false)
    private AnimalType animalType;

    @ManyToOne
    @JoinColumn(name = "rfid_type_id", nullable = false)
    private RfidType rfidType;

    @Column(length = 4, nullable = false)
    private Integer birthYear;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer animalCount;

    @UpdateTimestamp
    private Timestamp updateDate;

}

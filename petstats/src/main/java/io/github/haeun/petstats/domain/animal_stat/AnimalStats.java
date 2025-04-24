package io.github.haeun.petstats.domain.animal_stat;

import io.github.haeun.petstats.domain.animal_type.AnimalType;
import io.github.haeun.petstats.domain.region.Region;
import io.github.haeun.petstats.domain.rfid_type.RfidType;
import io.github.haeun.petstats.domain.species.Species;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
public class AnimalStats {
    @Id
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

    @Column(columnDefinition = "TINYINT(1)", nullable = false)
    @ColumnDefault("0")
    private boolean isDangerous;

    @LastModifiedDate
    private LocalDateTime updateDate;

}

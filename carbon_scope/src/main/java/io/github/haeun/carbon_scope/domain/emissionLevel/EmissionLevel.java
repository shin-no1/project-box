package io.github.haeun.carbon_scope.domain.emissionLevel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@IdClass(EmissionLevelId.class)
public class EmissionLevel {

    @Id
    private String gasType;

    @Id
    private String levelName;

    private Double minValue;
    private Double maxValue;

    @Column(length = 20)
    private String levelLabel;

    @Column(columnDefinition = "TEXT")
    private String description;

}

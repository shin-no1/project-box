package io.github.haeun.carbon_scope.domain.emissionLevel;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class EmissionLevelId implements Serializable {
    private String gasType;
    private String levelName;
}

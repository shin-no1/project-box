package io.github.haeun.petstats.web.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RegionTopAnimalTypeRequest {
    private Integer regionId;

    @Builder
    public RegionTopAnimalTypeRequest(Integer regionId) {
        this.regionId = regionId;
    }
}

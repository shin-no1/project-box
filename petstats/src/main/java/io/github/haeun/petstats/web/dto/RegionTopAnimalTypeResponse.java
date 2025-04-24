package io.github.haeun.petstats.web.dto;

import lombok.Getter;

@Getter
public class RegionTopAnimalTypeResponse {
    private final String species;
    private final String type;
    private final Integer count;

    public RegionTopAnimalTypeResponse(String species, String type, Integer count) {
        this.species = species;
        this.type = type;
        this.count = count;
    }
}

package io.github.haeun.petstats.web.dto;

import lombok.Getter;

@Getter
public class AnimalStatsTrendResponse {
    private final String city;
    private final String species;
    private final Integer birthYear;
    private final Integer count;

    public AnimalStatsTrendResponse(String city, String species, Integer birthYear, Integer count) {
        this.city = city;
        this.species = species;
        this.birthYear = birthYear;
        this.count = count;
    }
}

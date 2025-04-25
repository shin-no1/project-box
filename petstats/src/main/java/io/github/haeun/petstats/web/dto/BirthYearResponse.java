package io.github.haeun.petstats.web.dto;

import lombok.Getter;

@Getter
public class BirthYearResponse {
    private final Integer birthYear;
    private final Boolean isSelected;

    public BirthYearResponse(Integer birthYear, Boolean isSelected) {
        this.birthYear = birthYear;
        this.isSelected = isSelected;
    }
}

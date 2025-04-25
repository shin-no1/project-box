package io.github.haeun.petstats.web.dto;

import lombok.Getter;

@Getter
public class RegionResponse {
    private final Integer id;
    private final String name;
    private final Boolean isSelected;

    public RegionResponse(Integer id, String name, Boolean isSelected) {
        this.id = id;
        this.name = name;
        this.isSelected = isSelected;
    }
}

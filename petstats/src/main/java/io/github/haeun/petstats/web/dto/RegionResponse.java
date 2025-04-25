package io.github.haeun.petstats.web.dto;

import lombok.Getter;

@Getter
public class RegionResponse {
    private final Integer id;
    private final String province;

    public RegionResponse(Integer id, String province) {
        this.id = id;
        this.province = province;
    }
}

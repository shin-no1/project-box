package io.github.haeun.petstats.web.dto;

import lombok.Getter;

@Getter
public class TopRfidTypeResponse {
    private final String rfid;
    private final String type;
    private final Integer count;

    public TopRfidTypeResponse(String rfid, String type, Integer count) {
        this.rfid = rfid;
        this.type = type;
        this.count = count;
    }
}

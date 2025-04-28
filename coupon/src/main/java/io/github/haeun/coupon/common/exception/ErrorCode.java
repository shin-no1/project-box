package io.github.haeun.coupon.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    COUPON_CREATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "쿠폰 생성에 실패했습니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}

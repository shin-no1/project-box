package io.github.haeun.coupon.web.advice;

import io.github.haeun.coupon.common.exception.CustomException;
import io.github.haeun.coupon.common.exception.ErrorCode;
import io.github.haeun.coupon.web.advice.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse response = new ErrorResponse(
                errorCode.getStatus().value(),
                errorCode.name(),
                errorCode.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(errorCode.getStatus()).body(response);
    }
}

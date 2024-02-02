package com.jointpurchases.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    NOT_FOUND_EMAIL(HttpStatus.BAD_REQUEST, "등록되지 않은 이메일입니다.");

    private final HttpStatus statusCode;
    private final String message;
}
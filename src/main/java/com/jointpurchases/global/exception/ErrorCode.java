package com.jointpurchases.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"내부 서버 오류가 발생했습니다."),

    DUPLICATE_CATEGORY(HttpStatus.BAD_REQUEST, "해당 카테고리가 이미 존재합니다."),
    NOT_FOUND_CATEGORY(HttpStatus.BAD_REQUEST, "해당 카테고리가 존재하지 않습니다."),

    UPLOAD_MAXIMUM_IMAGE(HttpStatus.BAD_REQUEST, "최대 3장의 이미지만 업로드할 수 있습니다."),
    FAIL_TO_UPLOAD_FILE(HttpStatus.BAD_REQUEST, "이미지 업로드에 실패했습니다.")
    ;

    private final HttpStatus statusCode;
    private final String message;
}
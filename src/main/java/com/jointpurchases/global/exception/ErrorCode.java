package com.jointpurchases.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류가 발생했습니다."),

    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "중복된 이메일 입니다."),
    INVALID_ADMIN_PASSWORD(HttpStatus.BAD_REQUEST, "관리자 암호가 틀려 등록이 불가합니다."),
    NOT_FOUND_EMAIL(HttpStatus.BAD_REQUEST, "등록되지 않은 이메일입니다."),

    DUPLICATE_CATEGORY(HttpStatus.BAD_REQUEST, "해당 카테고리가 이미 존재합니다."),
    NOT_FOUND_CATEGORY(HttpStatus.BAD_REQUEST, "해당 카테고리가 존재하지 않습니다."),

    UPLOAD_MAXIMUM_IMAGE(HttpStatus.BAD_REQUEST, "최대 3장의 이미지만 업로드할 수 있습니다."),
    FAIL_TO_UPLOAD_FILE(HttpStatus.BAD_REQUEST, "이미지 업로드에 실패했습니다."),

    ALREADY_EXISTS_PRODUCT_IN_CART(HttpStatus.CONFLICT, "이미 장바구니에 담긴 상품 입니다."),
    NOT_EXISTS_PRODUCT_IN_CART(HttpStatus.NOT_FOUND, "장바구니의 상품이 없습니다."),

    ALREADY_EXISTS_USER_CART(HttpStatus.BAD_REQUEST, "사용자의 장바구니가 이미 존재합니다."),
    NOT_EXISTS_USER_CART(HttpStatus.NOT_FOUND, "사용자의 장바구니가 없습니다."),

    NOT_EXISTS_USERID(HttpStatus.NOT_FOUND, "존재하지 않는 아이디 입니다."),

    DO_NOT_CHANGE_THE_QUANTITY_EXCEEDING_THE_STOCK(HttpStatus.BAD_REQUEST, "상품의 남은 수량이 부족합니다."),
    NOT_EXISTS_PRODUCT(HttpStatus.NOT_FOUND, "존재하지 않는 상품 입니다."),
    PRODUCT_CANNOT_BE_LESS_THAN_ONE(HttpStatus.BAD_REQUEST, "장바구니의 상품 수량은 1보다 작을 수 없습니다."),

    NO_POINT_USING_HISTORY(HttpStatus.NOT_FOUND, "포인트 사용 내역이 없습니다."),
    NOT_ENOUGH_POINT_BALANCE(HttpStatus.BAD_REQUEST, "포인트 잔액이 부족합니다."),

    PRODUCT_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당상품이 존재하지 않습니다."),
    NOT_PRODUCT_BY_USER(HttpStatus.BAD_REQUEST, "회원님이 등록한 상품이 아닙니다."),
    ;

    private final HttpStatus statusCode;
    private final String message;
}
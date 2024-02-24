package com.jointpurchases.domain.point.exception;

import com.jointpurchases.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PointException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String errorMessage;

    public PointException(ErrorCode errorCode){
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getMessage();
    }
}
package com.jointpurchases.domain.auth.exception;

import com.jointpurchases.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserException extends RuntimeException {
    private ErrorCode errorCode;

}
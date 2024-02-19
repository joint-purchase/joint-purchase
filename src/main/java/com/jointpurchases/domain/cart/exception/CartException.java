package com.jointpurchases.domain.cart.exception;

import com.jointpurchases.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CartException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String errorMessage;

    public CartException(ErrorCode errorCode){
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getMessage();
    }
}

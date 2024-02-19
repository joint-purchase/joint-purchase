package com.jointpurchases.domain.product.exception;

import com.jointpurchases.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ImageFailToUploadException extends RuntimeException {
    private ErrorCode errorCode;
}
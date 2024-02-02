package com.jointpurchases.global.advice;


import com.jointpurchases.domain.product.exception.ImageFailToUploadException;
import com.jointpurchases.global.common.ServiceResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ImageFailToUploadException.class)
    public ResponseEntity<?> ImageFailToUploadExceptionHandler(ImageFailToUploadException e){
        return ResponseEntity.status(e.getErrorCode().getStatusCode())
                .body(ServiceResult.fail(e.getErrorCode()));
    }
}
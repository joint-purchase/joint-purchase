package com.jointpurchases.global.advice;


import com.jointpurchases.domain.category.exception.CategoryException;
import com.jointpurchases.domain.product.exception.ImageFailToUploadException;
import com.jointpurchases.domain.product.exception.ProductException;
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

    @ExceptionHandler(ProductException.class)
    public ResponseEntity<?> ProductExceptionHandler(ProductException e){
        return ResponseEntity.status(e.getErrorCode().getStatusCode())
                .body(ServiceResult.fail(e.getErrorCode()));
    }

    @ExceptionHandler(CategoryException.class)
    public ResponseEntity<?> CategoryExceptionHandler(CategoryException e){
        return ResponseEntity.status(e.getErrorCode().getStatusCode())
                .body(ServiceResult.fail(e.getErrorCode()));
    }
}
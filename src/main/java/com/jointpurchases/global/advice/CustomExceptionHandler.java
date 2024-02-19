package com.jointpurchases.global.advice;


import com.jointpurchases.domain.cart.exception.CartException;
import com.jointpurchases.domain.category.exception.CategoryException;
import com.jointpurchases.domain.point.exception.PointException;
import com.jointpurchases.domain.product.exception.ImageFailToUploadException;
import com.jointpurchases.global.common.ServiceResult;
import com.jointpurchases.global.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ImageFailToUploadException.class)
    public ResponseEntity<?> ImageFailToUploadExceptionHandler(ImageFailToUploadException e) {
        return ResponseEntity.status(e.getErrorCode().getStatusCode())
                .body(ServiceResult.fail(e.getErrorCode()));
    }

    @ExceptionHandler(CategoryException.class)
    public ResponseEntity<?> postExceptionHandler(CategoryException e) {
        return ResponseEntity.status(e.getErrorCode().getStatusCode())
                .body(ServiceResult.fail(e.getErrorCode()));
    }

    @ExceptionHandler(PointException.class)
    public ResponseEntity<?> pointExceptionHandler(PointException e) {

        return new ResponseEntity<>(e, e.getErrorCode().getStatusCode());
    }

    @ExceptionHandler(CartException.class)
    public ResponseEntity<?> cartExceptionHandler(CartException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error(e.getErrorCode().getStatusCode())
                .code(e.getErrorCode().getStatusCode().value())
                .message(e.getErrorMessage())
                .build();

        return new ResponseEntity<>(errorResponse, e.getErrorCode().getStatusCode());
    }
}
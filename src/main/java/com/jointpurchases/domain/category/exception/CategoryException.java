package com.jointpurchases.domain.category.exception;

import com.jointpurchases.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryException extends RuntimeException{
    ErrorCode errorCode;
}
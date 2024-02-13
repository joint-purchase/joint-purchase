package com.jointpurchases.domain.review.exception;

public class InvalidFileException extends RuntimeException{
    private static final String MESSAGE = "파일은 최대 5개 업로드 가능합니다";

    public InvalidFileException(){
        super(MESSAGE);
    }
}

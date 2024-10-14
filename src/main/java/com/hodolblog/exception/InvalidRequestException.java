package com.hodolblog.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidRequestException extends GlobalException {
    public static final String MESSAGE = "잘못된 요청 입니다.";

    public InvalidRequestException(String fieldName, String message) {
        super(MESSAGE);
        super.addValidation(fieldName, message);
    }

    @Override
    public int statusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
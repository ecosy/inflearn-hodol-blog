package com.hodolblog.exception;

import org.springframework.http.HttpStatus;

public class PostNotFoundException extends GlobalException {

    public static final String MESSAGE = "존재하지 않는 글 입니다.";

    public PostNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int statusCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}

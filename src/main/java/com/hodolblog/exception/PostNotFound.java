package com.hodolblog.exception;

public class PostNotFound extends RuntimeException{

    public static final String MESSAGE = "존재하지 않는 글 입니다.";
    public PostNotFound() {
        super(MESSAGE);
    }
}

package com.hodolblog.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class GlobalException extends RuntimeException {
    private final Map<String, String> validation = new HashMap<>();

    protected GlobalException(String message) {
        super(message);
    }

    public abstract int statusCode();

    public void addValidation(String fieldName, String message) {
        this.validation.put(fieldName, message);
    }
}
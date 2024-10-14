package com.hodolblog.controller;

import com.hodolblog.exception.GlobalException;
import com.hodolblog.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class ExceptionController {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e) {
        Map<String, String> validation = new HashMap<>();
        for (FieldError fieldError : e.getFieldErrors()) {
            validation.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ErrorResponse.builder()
                            .code("400")
                            .message("잘못된 요청 입니다.")
                            .validation(validation)
                            .build();
    }

    @ResponseBody
    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ErrorResponse> globalExceptionHandler(GlobalException e) {
        ErrorResponse responseBody = ErrorResponse.builder()
                                                  .code(String.valueOf(e.statusCode()))
                                                  .message(e.getMessage())
                                                  .validation(e.getValidation())
                                                  .build();

        return ResponseEntity.status(e.statusCode())
                             .body(responseBody);
    }
}
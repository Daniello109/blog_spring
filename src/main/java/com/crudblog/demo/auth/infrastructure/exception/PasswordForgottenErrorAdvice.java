package com.crudblog.demo.auth.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public class PasswordForgottenErrorAdvice {
    @ExceptionHandler(PasswordForgottenErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String errorRegistrationHandler(PasswordForgottenErrorException ex) {
        return ex.getMessage();
    }
}

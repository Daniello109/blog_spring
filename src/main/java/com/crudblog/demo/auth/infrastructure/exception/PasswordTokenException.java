package com.crudblog.demo.auth.infrastructure.exception;

public class PasswordTokenException extends RuntimeException{
    public PasswordTokenException() {
        super("Token is not valid");
    }
}

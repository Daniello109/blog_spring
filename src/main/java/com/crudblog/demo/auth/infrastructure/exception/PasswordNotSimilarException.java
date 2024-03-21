package com.crudblog.demo.auth.infrastructure.exception;

public class PasswordNotSimilarException extends RuntimeException{
    public PasswordNotSimilarException() {
        super("Passwords are not similar");
    }
}

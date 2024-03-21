package com.crudblog.demo.auth.infrastructure.exception;

public class RegistrationErrorException extends RuntimeException{
    public RegistrationErrorException(String message) {
        super(message);
    }
}

package com.crudblog.demo.auth.infrastructure.repository.exception;

public class RegistrationErrorException extends RuntimeException{
    public RegistrationErrorException(String message) {
        super(message);
    }
}

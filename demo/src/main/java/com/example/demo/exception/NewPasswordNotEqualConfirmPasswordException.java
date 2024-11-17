package com.example.demo.exception;

public class NewPasswordNotEqualConfirmPasswordException extends RuntimeException {
    public NewPasswordNotEqualConfirmPasswordException() {
        super();
    }

    public NewPasswordNotEqualConfirmPasswordException(String message) {
        super(message);
    }

    public NewPasswordNotEqualConfirmPasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}
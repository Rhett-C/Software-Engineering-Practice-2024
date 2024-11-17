package com.example.demo.exception;

public class IncorrectOldPasswordException extends RuntimeException {
    public IncorrectOldPasswordException() {
        super();
    }

    public IncorrectOldPasswordException(String message) {
        super(message);
    }

    public IncorrectOldPasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}
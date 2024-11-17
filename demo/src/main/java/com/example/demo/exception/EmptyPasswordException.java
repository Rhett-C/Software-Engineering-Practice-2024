package com.example.demo.exception;

public class EmptyPasswordException extends RuntimeException {
    public EmptyPasswordException() {
        super();
    }

    public EmptyPasswordException(String message) {
        super(message);
    }

    public EmptyPasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}

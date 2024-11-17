package com.example.demo.exception;

public class TokenErrorException extends RuntimeException {
    public TokenErrorException() {
        super();
    }

    public TokenErrorException(String message) {
        super(message);
    }

    public TokenErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}

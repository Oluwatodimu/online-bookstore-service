package com.todimu.backend.bookstoreservice.exception;

public class TokenValidationFailedException extends RuntimeException {

    public TokenValidationFailedException(String message) {
        super(message);
    }
}

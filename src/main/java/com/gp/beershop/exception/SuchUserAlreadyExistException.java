package com.gp.beershop.exception;

public class SuchUserAlreadyExistException extends Exception {
    public SuchUserAlreadyExistException(final String message) {
        super(message);
    }
}

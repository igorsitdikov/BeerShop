package com.gp.beershop.exception;

public class NoSuchUserException extends BusinessLogicException {
    public NoSuchUserException(final String message) {
        super(message);
    }
}

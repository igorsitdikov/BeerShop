package com.gp.beershop.exception;

public class NoSuchUserException extends Exception {
    public NoSuchUserException(final String message) {
        super(message);
    }
}

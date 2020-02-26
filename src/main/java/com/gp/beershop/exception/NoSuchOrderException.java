package com.gp.beershop.exception;

public class NoSuchOrderException extends BusinessLogicException {
    public NoSuchOrderException(final String message) {
        super(message);
    }
}

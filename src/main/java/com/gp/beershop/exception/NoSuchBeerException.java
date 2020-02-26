package com.gp.beershop.exception;

public class NoSuchBeerException extends BusinessLogicException {
    public NoSuchBeerException(final String message) {
        super(message);
    }
}

package com.gp.beershop.exception;

public class SuchBeerAlreadyExistException extends BusinessLogicException {
    public SuchBeerAlreadyExistException(final String message) {
        super(message);
    }
}

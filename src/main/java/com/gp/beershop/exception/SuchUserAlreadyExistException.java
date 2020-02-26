package com.gp.beershop.exception;

public class SuchUserAlreadyExistException extends BusinessLogicException {
    public SuchUserAlreadyExistException(final String message) {
        super(message);
    }
}

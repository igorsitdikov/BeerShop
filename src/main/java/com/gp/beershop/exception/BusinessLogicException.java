package com.gp.beershop.exception;

public abstract class BusinessLogicException extends Exception {
    public BusinessLogicException(final String message) {
        super(message);
    }
}

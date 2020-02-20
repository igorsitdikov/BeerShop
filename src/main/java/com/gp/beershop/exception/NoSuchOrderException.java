package com.gp.beershop.exception;

public class NoSuchOrderException extends Exception {
    public NoSuchOrderException(final String message) {
        super(message);
    }
}

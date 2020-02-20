package com.gp.beershop.exception;

public class NoSuchBeerException extends Exception {
    public NoSuchBeerException(final String message) {
        super(message);
    }
}

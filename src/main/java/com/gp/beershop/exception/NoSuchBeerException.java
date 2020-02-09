package com.gp.beershop.exception;

public class NoSuchBeerException extends Exception {
    public NoSuchBeerException() {
    }

    public NoSuchBeerException(String message) {
        super(message);
    }
}

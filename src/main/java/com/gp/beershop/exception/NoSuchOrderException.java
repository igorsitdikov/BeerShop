package com.gp.beershop.exception;

public class NoSuchOrderException extends Exception {
    public NoSuchOrderException() {
    }

    public NoSuchOrderException(String message) {
        super(message);
    }
}

package com.gp.beershop.exception;

public class OrderIsEmptyException extends Exception {
    public OrderIsEmptyException(final String message) {
        super(message);
    }
}

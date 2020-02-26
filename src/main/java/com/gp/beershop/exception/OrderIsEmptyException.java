package com.gp.beershop.exception;

public class OrderIsEmptyException extends BusinessLogicException {
    public OrderIsEmptyException(final String message) {
        super(message);
    }
}

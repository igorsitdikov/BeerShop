package com.gp.beershop.exception;

public class NoSuchCustomerException extends Exception {
    public NoSuchCustomerException() {
    }

    public NoSuchCustomerException(String message) {
        super(message);
    }
}

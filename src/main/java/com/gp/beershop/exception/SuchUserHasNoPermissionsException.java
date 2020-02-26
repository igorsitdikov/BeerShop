package com.gp.beershop.exception;

public class SuchUserHasNoPermissionsException extends Exception {
    public SuchUserHasNoPermissionsException(final String message) {
        super(message);
    }
}

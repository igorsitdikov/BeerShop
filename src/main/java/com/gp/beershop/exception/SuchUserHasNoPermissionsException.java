package com.gp.beershop.exception;

public class SuchUserHasNoPermissionsException extends BusinessLogicException {
    public SuchUserHasNoPermissionsException(final String message) {
        super(message);
    }
}

package com.gp.beershop.exception;

import org.springframework.http.HttpStatus;

public class NoSuchUserException extends BusinessLogicException {
    public NoSuchUserException(final String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}

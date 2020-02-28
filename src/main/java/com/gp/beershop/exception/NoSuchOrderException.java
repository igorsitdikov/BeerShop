package com.gp.beershop.exception;

import org.springframework.http.HttpStatus;

public class NoSuchOrderException extends BusinessLogicException {
    public NoSuchOrderException(final String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}

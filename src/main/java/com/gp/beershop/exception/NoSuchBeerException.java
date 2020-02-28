package com.gp.beershop.exception;

import org.springframework.http.HttpStatus;

public class NoSuchBeerException extends BusinessLogicException {
    public NoSuchBeerException(final String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}

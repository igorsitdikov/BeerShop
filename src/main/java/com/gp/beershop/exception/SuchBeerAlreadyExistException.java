package com.gp.beershop.exception;

import org.springframework.http.HttpStatus;

public class SuchBeerAlreadyExistException extends BusinessLogicException {
    public SuchBeerAlreadyExistException(final String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.CONFLICT;
    }
}

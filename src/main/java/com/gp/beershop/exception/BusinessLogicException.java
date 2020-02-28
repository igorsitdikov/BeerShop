package com.gp.beershop.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class BusinessLogicException extends Exception {
    protected HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    public BusinessLogicException(final String message) {
        super(message);
    }
}

package com.gp.beershop.controller;

import com.gp.beershop.exception.NoSuchBeerException;
import com.gp.beershop.exception.NoSuchCustomerException;
import com.gp.beershop.exception.NoSuchOrderException;
import com.gp.beershop.exception.SuchUserAlreadyExistException;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.logging.Level;

@ControllerAdvice
@Log
public class ExceptionControllerAdvice {
    @ExceptionHandler({
        NoSuchCustomerException.class, NoSuchOrderException.class, NoSuchBeerException.class,
        UsernameNotFoundException.class, SuchUserAlreadyExistException.class
    })
    private ResponseEntity<ErrorMessage> handleBadRequest(final Exception e) {
        log.log(Level.SEVERE, e.getMessage(), e);
        return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @Data
    public static class ErrorMessage {
        private final String errorMessage;
    }
}

package com.gp.beershop.controller;

import com.gp.beershop.exception.BusinessLogicException;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.logging.Level;

@ControllerAdvice
@Log
public class ExceptionControllerAdvice {
    @ExceptionHandler(BusinessLogicException.class)
    private ResponseEntity<ErrorMessage> handleBadRequest(final BusinessLogicException e) {
        log.log(Level.SEVERE, e.getMessage(), e);
        return new ResponseEntity<>(new ErrorMessage(e.getMessage()), e.getHttpStatus());
    }

    @Data
    public static class ErrorMessage {
        private final String errorMessage;
    }
}

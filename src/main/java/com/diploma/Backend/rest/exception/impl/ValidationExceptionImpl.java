package com.diploma.Backend.rest.exception.impl;

import com.diploma.Backend.rest.exception.AbstractCustomException;
import org.springframework.http.HttpStatus;


public class ValidationExceptionImpl extends AbstractCustomException {
    public ValidationExceptionImpl(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}

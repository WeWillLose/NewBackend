package com.diploma.Backend.rest.exception.impl;

import com.diploma.Backend.rest.exception.AbstractCustomException;
import org.springframework.http.HttpStatus;

public class ForbiddenExceptionImpl extends AbstractCustomException {
    public ForbiddenExceptionImpl(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
    public ForbiddenExceptionImpl() {
        super("Недостаточно прав", HttpStatus.FORBIDDEN);
    }
}

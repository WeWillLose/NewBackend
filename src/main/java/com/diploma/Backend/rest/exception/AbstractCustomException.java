package com.diploma.Backend.rest.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
@Data
public class AbstractCustomException extends RuntimeException {
    private final HttpStatus httpStatus;

    public AbstractCustomException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}

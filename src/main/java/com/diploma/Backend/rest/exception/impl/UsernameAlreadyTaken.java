package com.diploma.Backend.rest.exception.impl;

import com.diploma.Backend.rest.exception.AbstractCustomException;
import org.springframework.http.HttpStatus;

public class UsernameAlreadyTaken extends AbstractCustomException {
    private static final long serialVersionUID = 1L;

    public UsernameAlreadyTaken(String username) {
        super(String.format("Пользователь с username: %s уже существует",username), HttpStatus.CONFLICT);
    }
}

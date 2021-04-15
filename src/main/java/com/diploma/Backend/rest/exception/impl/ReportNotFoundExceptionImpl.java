package com.diploma.Backend.rest.exception.impl;

import com.diploma.Backend.rest.exception.AbstractCustomException;
import org.springframework.http.HttpStatus;

public class ReportNotFoundExceptionImpl  extends AbstractCustomException {

    public ReportNotFoundExceptionImpl(Long id) {
        super(String.format("Отчет с id: %s не найден",id), HttpStatus.NOT_FOUND);
    }

    public ReportNotFoundExceptionImpl(String name) {
        super(String.format("Отчет с именем: %s не найден",name), HttpStatus.NOT_FOUND);
    }

    public ReportNotFoundExceptionImpl() {
        super("Отчет не найдеа",HttpStatus.NOT_FOUND);
    }
}

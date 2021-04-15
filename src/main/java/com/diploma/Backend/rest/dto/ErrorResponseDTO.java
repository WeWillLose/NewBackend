package com.diploma.Backend.rest.dto;

import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;

public class ErrorResponseDTO {

    private final List<String> messages;

    private final HttpStatus httpStatus;

    public ErrorResponseDTO(@NonNull String message, @NonNull HttpStatus httpStatus) {
        this.messages = List.of(message);
        this.httpStatus = httpStatus;
    }

    public ErrorResponseDTO(@NonNull List<String> messages, @NonNull HttpStatus httpStatus) {
        this.messages = messages;
        this.httpStatus = httpStatus;
    }

    public ResponseEntity<Object> getResponseEntity(){
        return ResponseEntity.status(httpStatus).body(new HashMap<>(){{put("messages",messages);}});
    }
}

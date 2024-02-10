package com.boardcamp.api.exceptions;

public class ExceptionUnprocessableEntity extends RuntimeException {
    public ExceptionUnprocessableEntity(String message) {
        super(message);
    }
}

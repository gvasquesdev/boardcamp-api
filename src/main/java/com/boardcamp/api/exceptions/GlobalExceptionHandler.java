package com.boardcamp.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {
    @ExceptionHandler({ ExceptionConflict.class })
    public ResponseEntity<String> handleConflict(ExceptionConflict exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler({ ExceptionUnprocessableEntity.class })
    public ResponseEntity<String> handleUnprocessableEntity(ExceptionUnprocessableEntity exception) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(exception.getMessage());
    }

    @ExceptionHandler({ ExceptionNotFound.class })
    public ResponseEntity<String> handleNotFound(ExceptionNotFound exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
}

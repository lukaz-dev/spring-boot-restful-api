package com.listatelefonica.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ConflictResourceException extends RuntimeException {

    public ConflictResourceException(String message) {
        super(message);
    }
}

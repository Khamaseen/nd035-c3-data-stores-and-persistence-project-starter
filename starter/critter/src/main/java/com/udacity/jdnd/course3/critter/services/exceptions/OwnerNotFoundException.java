package com.udacity.jdnd.course3.critter.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Owner not found")
public class OwnerNotFoundException extends RuntimeException {

    public OwnerNotFoundException() {
    }

    public OwnerNotFoundException(String message) {
        super(message);
    }
}
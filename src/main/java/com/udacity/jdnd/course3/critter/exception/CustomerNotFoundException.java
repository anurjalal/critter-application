package com.udacity.jdnd.course3.critter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException() {
        super("Pet not found");
    }

    public CustomerNotFoundException(Long id) {
        super(String.format("Pet not found with id = %d", id));
    }
}

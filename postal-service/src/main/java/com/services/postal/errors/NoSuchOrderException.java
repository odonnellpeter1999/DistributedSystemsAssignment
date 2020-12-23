package com.services.postal.errors;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoSuchOrderException extends RuntimeException {

    public NoSuchOrderException() {
        super("No such order was found");
    }
}
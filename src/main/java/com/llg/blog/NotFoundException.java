package com.llg.blog;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


//  -> 404
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {


    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

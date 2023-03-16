package com.zensar.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BlogApiException extends RuntimeException{

    private String message;


    public BlogApiException(String message) {
        super(message);

    }
}

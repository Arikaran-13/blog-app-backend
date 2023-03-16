package com.zensar.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object>handleResourceNotFound(ResourceNotFoundException ex , WebRequest rq){

        String msg = ex.getMessage();

        return handleExceptionInternal(ex,msg,new HttpHeaders(),HttpStatus.NOT_FOUND,rq);
    }
    @ExceptionHandler(BlogApiException.class)
    public ResponseEntity<Object>handleBlogApiException(BlogApiException ex , WebRequest rq){
        String msg = ex.getMessage();
        return handleExceptionInternal(ex,msg,new HttpHeaders(),HttpStatus.NOT_FOUND,rq);
    }
}

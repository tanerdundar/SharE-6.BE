package com.tanerdundar.sharer.exceptionHandlers.handlers;

import com.tanerdundar.sharer.exceptionHandlers.exceptions.PasswordException;
import com.tanerdundar.sharer.exceptionHandlers.exceptions.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class PasswordExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value
            = { IllegalArgumentException.class, IllegalStateException.class })
    protected ResponseEntity handleConflict(
            RuntimeException ex, WebRequest request) {
        return new ResponseEntity("IllegalArgument exception", HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(value= {PasswordException.class, PasswordException.class })
    protected ResponseEntity handleNotFoundConflict(
            RuntimeException ex, WebRequest request) {

        return new ResponseEntity(ex.getMessage()==null?"NOT FOUND":ex.getMessage(),HttpStatus.NOT_FOUND);
    }

}
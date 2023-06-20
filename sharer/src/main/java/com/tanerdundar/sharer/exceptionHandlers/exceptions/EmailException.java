package com.tanerdundar.sharer.exceptionHandlers.exceptions;

public class EmailException extends RuntimeException{

    public EmailException() {
        super("Email not found.");
    }

    public EmailException(String message) {
        super(message);
    }
}

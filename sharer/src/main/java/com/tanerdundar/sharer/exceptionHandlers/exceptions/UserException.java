package com.tanerdundar.sharer.exceptionHandlers.exceptions;

public class UserException extends RuntimeException{

    public UserException() {
        super("User not found.");
    }

    public UserException(String message) {
        super(message);
    }
}

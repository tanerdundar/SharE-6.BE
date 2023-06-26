package com.tanerdundar.sharer.exceptionHandlers.exceptions;

public class PasswordException extends RuntimeException{

    public PasswordException() {
        super("Password false");
    }

    public PasswordException(String message) {
        super(message);
    }
}

package com.tanerdundar.sharer.exceptionHandlers.exceptions;

public class MeowException extends RuntimeException {

    public MeowException() {
        super("Wrong meow format..");
    }

    public MeowException(String message) {
        super(message);
    }
}

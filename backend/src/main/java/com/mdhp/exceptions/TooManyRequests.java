package com.mdhp.exceptions;


public class TooManyRequests  extends Exception {
    private final String message;

    public TooManyRequests(final String exMessage, final String message) {
        super(exMessage);
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public String getDefaultMessage() {
        return super.getMessage();
    }
}

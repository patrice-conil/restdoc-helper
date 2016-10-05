package com.pconil.restdoc.exception;

/**
 * Sample com.pconil.restdoc.exception to show how exceptionHandling works.
 */
public class SampleException extends RuntimeException {

    /**
     * The error.
     */
    private Error error;

    /**
     * Constructor.
     * @param message the message to store
     */
    public SampleException(String message) {
        super(message);
        error = new Error(message);
    }

    public Error getError() {
        return error;
    }
}

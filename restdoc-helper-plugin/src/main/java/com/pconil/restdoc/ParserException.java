package com.pconil.restdoc;

/**
 * Exception that indicates an error while creating the parser.
 */
public class ParserException extends RuntimeException {

    public String getMessage() {
        return message;
    }

    /**
     * Explanation of error.
     */
    private String message = null;

    /**
     * Constructor.
     *
     * @param message the messages that explains the error.
     */
    public ParserException(String message) {
        this.message = message;
    }
}

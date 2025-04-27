package com.wholesale.demo.exception;

/**
 * Exception thrown when an amount is greater or less than expected.
 * This is a custom exception used to handle specific cases where
 * the amount falls outside the acceptable range.
 */

public class AmountGreateOrLessException extends RuntimeException {

    /**
     * Constructor to create an instance of AmountGreaterOrLessException with a specific error message.
     *
     * @param message the detail message explaining the exception
     */
    public AmountGreateOrLessException(String message) {
        super(message);  // Pass the message to the superclass (RuntimeException)
    }
}
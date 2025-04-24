package com.wholesale.demo.exception;

public class AmountGreateOrLessException extends RuntimeException {
    public AmountGreateOrLessException(String message) {
        super(message);
    }
}

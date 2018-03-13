package com.excilys.formation.cdb.exceptions;

public class ValidationException extends Exception {

    public ValidationException() {

    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable throwable) {
        super(message, throwable);
    }
}

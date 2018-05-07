package com.excilys.formation.cdb.exceptions;

public class UnauthorizedLimitValueException extends Exception {

    public UnauthorizedLimitValueException() {

    }

    public UnauthorizedLimitValueException(String message) {
        super(message);
    }

    public UnauthorizedLimitValueException(String message, Throwable throwable) {
        super(message, throwable);
    }
}

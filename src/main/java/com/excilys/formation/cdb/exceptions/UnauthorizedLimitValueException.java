package com.excilys.formation.cdb.exceptions;

public class UnauthorizedLiimitValueException extends Exception {

    public MissingElementException() {

    }

    public MissingElementException(String message) {
        super(message);
    }

    public MissingElementException(String message, Throwable throwable) {
        super(message, throwable);
    }
}

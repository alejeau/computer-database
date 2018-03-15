package com.excilys.formation.cdb.exceptions;

public class MissingElementException extends ValidationException {

    public MissingElementException() {

    }

    public MissingElementException(String message) {
        super(message);
    }

    public MissingElementException(String message, Throwable throwable) {
        super(message, throwable);
    }
}

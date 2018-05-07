package com.excilys.formation.cdb.exceptions;

public class DateException extends ValidationException {

    public DateException(String message) {
        super(message);
    }

    public DateException(String message, Throwable throwable) {
        super(message, throwable);
    }
}

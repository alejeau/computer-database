package com.excilys.formation.cdb.exceptions;

public class ControllerException extends Exception {

    public ControllerException(String message) {
        super(message);
    }

    public ControllerException(Throwable cause) {
        super(cause);
    }

    public ControllerException(String message, Throwable throwable) {
        super(message, throwable);
    }
}

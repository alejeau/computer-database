package com.excilys.formation.cdb.exceptions;

public class ConnectionException extends Exception {

    public ConnectionException(String message) {
        super(message);
    }

    public ConnectionException(String message, Throwable throwable) {
        super(message, throwable);
    }
}

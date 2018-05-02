package com.excilys.formation.cdb.exceptions;

public class MapperException extends Exception {
    public MapperException() {
    }

    public MapperException(String message) {
        super(message);
    }

    public MapperException(Throwable cause) {
        super(cause);
    }

    public MapperException(String message, Throwable cause) {
        super(message, cause);
    }
}

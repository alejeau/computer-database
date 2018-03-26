package com.excilys.formation.cdb.exceptions;

public class DAOException extends Exception {

    public DAOException(String message) {
        super(message);
    }

    public DAOException(String message, Throwable throwable) {
        super(message, throwable);
    }
}

package com.excilys.formation.cdb.exceptions;

public class ServiceException extends DAOException {

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable throwable) {
        super(message, throwable);
    }
}

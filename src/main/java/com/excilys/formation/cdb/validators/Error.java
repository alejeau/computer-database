package com.excilys.formation.cdb.validators;

public class Error {
    private Field field;
    private String message;

    public Error(Field field, String message) {
        this.field = field;
        this.message = message;
    }

    public Field getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Error{" +
                "field=" + field +
                ", message='" + message + '\'' +
                '}';
    }
}

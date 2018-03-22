package com.excilys.formation.cdb.validators;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Error error = (Error) o;
        return field == error.field && Objects.equals(message, error.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field, message);
    }
}

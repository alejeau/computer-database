package com.excilys.formation.cdb.service.validators.core;

import java.util.Objects;

public class Error {
    private FieldName fieldName;
    private String message;

    public Error(FieldName fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }

    public FieldName getFieldName() {
        return fieldName;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldName, message);
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
        return fieldName == error.fieldName && Objects.equals(message, error.message);
    }

    @Override
    public String toString() {
        return String.format("%s %s", fieldName.toString(), message);
    }
}

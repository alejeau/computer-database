package com.excilys.formation.cdb.persistence;

import com.excilys.formation.cdb.model.constants.ComputerField;
import com.excilys.formation.cdb.model.constants.DbFields;

public enum DatabaseField {
    COMPUTER_NAME(DbFields.COMPUTER_NAME),
    INTRODUCED(DbFields.COMPUTER_INTRODUCED),
    DISCONTINUED(DbFields.COMPUTER_DISCONTINUED),
    COMPANY_NAME(DbFields.COMPANY_NAME);

    private final String value;

    DatabaseField(final String newValue) {
        value = newValue;
    }

    public String getValue() {
        return value;
    }

    public static DatabaseField toDatabaseField(ComputerField computerField) {
        switch (computerField) {
            case COMPUTER_NAME:
                return DatabaseField.COMPUTER_NAME;
            case INTRODUCED:
                return DatabaseField.INTRODUCED;
            case DISCONTINUED:
                return DatabaseField.DISCONTINUED;
            case COMPANY_NAME:
                return DatabaseField.COMPANY_NAME;
            default:
                return DatabaseField.COMPUTER_NAME;
        }
    }
}

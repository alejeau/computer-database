package com.excilys.formation.cdb.persistence;

import com.excilys.formation.cdb.persistence.dao.impl.DbFields;

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
}

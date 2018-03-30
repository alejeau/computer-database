package com.excilys.formation.cdb.mapper;

import com.excilys.formation.cdb.persistence.DatabaseField;
import com.excilys.formation.cdb.servlets.constants.ComputerField;

public class DatabaseFieldsMapper {

    private DatabaseFieldsMapper() {
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

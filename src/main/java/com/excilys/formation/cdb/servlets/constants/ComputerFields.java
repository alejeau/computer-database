package com.excilys.formation.cdb.servlets.constants;

public enum ComputerFields {
    COMPUTER_NAME("computerName"),
    INTRODUCED("introduced"),
    DISCONTINUED("discontinued"),
    COMPANY_NAME("companyName");

    private final String value;

    ComputerFields(final String newValue) {
        value = newValue;
    }

    public String getValue() {
        return value;
    }
}

package com.excilys.formation.cdb.servlets.constants;

public enum ComputerField {
    COMPUTER_NAME("computerName"),
    INTRODUCED("introduced"),
    DISCONTINUED("discontinued"),
    COMPANY_NAME("companyName");

    private final String value;

    ComputerField(final String newValue) {
        value = newValue;
    }

    public String getValue() {
        return value;
    }
}

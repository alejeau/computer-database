package com.excilys.formation.cdb.servlets.constants;

public enum ComputerField {
    COMPUTER_NAME(ServletParameter.COMPUTER_NAME),
    INTRODUCED(ServletParameter.INTRODUCED),
    DISCONTINUED(ServletParameter.DISCONTINUED),
    COMPANY_NAME(ServletParameter.COMPANY_NAME);

    private final String value;

    ComputerField(final String newValue) {
        value = newValue;
    }

    public String getValue() {
        return value;
    }
}

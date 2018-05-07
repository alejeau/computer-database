package com.excilys.formation.cdb.model.constants;

public enum ComputerField {
    COMPUTER_NAME(ControllerParameters.COMPUTER_NAME),
    INTRODUCED(ControllerParameters.INTRODUCED),
    DISCONTINUED(ControllerParameters.DISCONTINUED),
    COMPANY_NAME(ControllerParameters.COMPANY_NAME);

    private final String value;

    ComputerField(final String newValue) {
        value = newValue;
    }

    public String getValue() {
        return value;
    }
}

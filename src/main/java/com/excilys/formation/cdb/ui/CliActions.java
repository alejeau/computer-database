package com.excilys.formation.cdb.ui;

public enum CliActions {
    VIEW_COMPUTER_LIST("1) View the list of computers"),
    VIEW_COMPANY_LIST("2) View the list of companies"),
    CHECK_COMPUTER_BY_ID("3) Check a computer by ID"),
    CHECK_COMPUTERS_BY_NAME("4) Check a computer by name"),
    ADD_COMPUTER("5) Add a computer to the list"),
    UPDATE_COMPUTER("6) Update a computer in the list (by id)"),
    DELETE_COMPUTER("7) Delete a computer in the list (by id)"),
    DELETE_COMPANY("8) Delete a company in the list (by id)"),
    EXIT("9) Exit this program");

    private final String value;

    CliActions(final String newValue) {
        value = newValue;
    }

    public static CliActions map(int choice) {
        switch (choice) {
            case 1:
                return VIEW_COMPUTER_LIST;
            case 2:
                return VIEW_COMPANY_LIST;
            case 3:
                return CHECK_COMPUTER_BY_ID;
            case 4:
                return CHECK_COMPUTERS_BY_NAME;
            case 5:
                return ADD_COMPUTER;
            case 6:
                return UPDATE_COMPUTER;
            case 7:
                return DELETE_COMPUTER;
            case 8:
                return DELETE_COMPANY;
            default:
                return EXIT;
        }
    }

    public String getValue() {
        return value;
    }
}









package com.excilys.formation.cdb.ui;

public enum CliActions {
    VIEW_COMPUTER_LIST("1) View the list of computers"),
    VIEW_COMPANY_LIST("2) View the list of companies"),
    CHECK_COMPUTER_BY_ID("3) Check a computer by ID"),
    CHECK_COMPUTERS_BY_NAME("4) Check a computer by name"),
    ADD_COMPUTER("5) Add a computer to the list"),
    UPDATE_COMPUTER("6) Update a computer in the list (by id)"),
    DELETE_COMPUTER("7) Delete a computer in the list (by id)"),
    EXIT("8) Exit this program");

    private final String value;

    CliActions(final String newValue) {
        value = newValue;
    }

    public String getValue() {
        return value;
    }
    public static CliActions map(int choice) {
        CliActions action = EXIT;
        switch (choice) {
            case 1:
                action = VIEW_COMPUTER_LIST;
                break;
            case 2:
                action = VIEW_COMPANY_LIST;
                break;
            case 3:
                action = CHECK_COMPUTER_BY_ID;
                break;
            case 4:
                action = CHECK_COMPUTERS_BY_NAME;
                break;
            case 5:
                action = ADD_COMPUTER;
                break;
            case 6:
                action = UPDATE_COMPUTER;
                break;
            case 7:
                action = DELETE_COMPUTER;
                break;
            default:
                break;
        }

        return action;
    }
}









package com.excilys.formation.cdb.ui;

public enum UserManagementActions {
    CHECK_USER("1) Check user with name"),
    ADD_USER("2) Add a user"),
    UPDATE_USER("3) Update a user"),
    DELETE_USER("4) Delete a user"),
    EXIT("5) Exit this management CLI");

    private final String value;

    UserManagementActions(final String newValue) {
        value = newValue;
    }

    public static UserManagementActions map(int choice) {
        switch (choice) {
            case 1:
                return CHECK_USER;
            case 2:
                return ADD_USER;
            case 3:
                return UPDATE_USER;
            case 4:
                return DELETE_USER;
            default:
                return EXIT;
        }
    }

    public String getValue() {
        return value;
    }
}









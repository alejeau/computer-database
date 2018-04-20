package com.excilys.formation.cdb.controllers.constants;

public class Paths {
    public static final String LOCAL_PATH_DASHBOARD = "/access";
    public static final String LOCAL_PATH_ADD_COMPUTER = "/access/computer/add";
    public static final String LOCAL_PATH_EDIT_COMPUTER = "/access/computer/edit";
    public static final String LOCAL_PATH_SEARCH_COMPUTER = "/access/computer/search";
    private static final String PREFIX = "/computer-database";
    public static final String ABSOLUTE_PATH_DASHBOARD = PREFIX + LOCAL_PATH_DASHBOARD;
    public static final String ABSOLUTE_PATH_ADD_COMPUTER = PREFIX + LOCAL_PATH_ADD_COMPUTER;
    public static final String ABSOLUTE_PATH_EDIT_COMPUTER = PREFIX + LOCAL_PATH_EDIT_COMPUTER;
    public static final String ABSOLUTE_PATH_SEARCH_COMPUTER = PREFIX + LOCAL_PATH_SEARCH_COMPUTER;
    private Paths() {
    }
}

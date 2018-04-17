package com.excilys.formation.cdb.controllers.constants;

public class Paths {
    private Paths() {
    }

//    private static final String PREFIX = "";
    private static final String PREFIX = "/computer-database";
    
    public static final String LOCAL_PATH_DASHBOARD = "/access";
    public static final String LOCAL_PATH_ADD_COMPUTER = "/access/add";
    public static final String LOCAL_PATH_EDIT_COMPUTER = "/access/edit";
    public static final String LOCAL_PATH_SEARCH_COMPUTER = "/access/search";

    public static final String ABSOLUTE_PATH_DASHBOARD = PREFIX + "/access";
    public static final String ABSOLUTE_PATH_ADD_COMPUTER = PREFIX + "/access/add";
    public static final String ABSOLUTE_PATH_EDIT_COMPUTER = PREFIX + "/access/edit";
    public static final String ABSOLUTE_PATH_SEARCH_COMPUTER = PREFIX + "/access/search";
}

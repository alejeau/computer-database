package com.excilys.formation.cdb.model.constants;

public class Paths {
    public static final String LOCAL_PATH_DASHBOARD = "/dashboard";
    public static final String LOCAL_PATH_SEARCH_COMPUTER = "/dashboard/search";
    public static final String LOCAL_PATH_ADD_COMPUTER = "/computer/add";
    public static final String LOCAL_PATH_EDIT_COMPUTER = "/computer/edit";
    public static final String LOCAL_PATH_LOGIN = "/login";

    public static final String PREFIX = "/computer-database";
    public static final String ABSOLUTE_PATH_DASHBOARD = String.format("%s%s", PREFIX, LOCAL_PATH_DASHBOARD);
    public static final String ABSOLUTE_PATH_ADD_COMPUTER = String.format("%s%s", PREFIX, LOCAL_PATH_ADD_COMPUTER);
    public static final String ABSOLUTE_PATH_EDIT_COMPUTER = String.format("%s%s", PREFIX, LOCAL_PATH_EDIT_COMPUTER);
    public static final String ABSOLUTE_PATH_SEARCH_COMPUTER = String.format("%s%s", PREFIX, LOCAL_PATH_SEARCH_COMPUTER);
    public static final String ABSOLUTE_PATH_LOGIN = String.format("%s%s", PREFIX, LOCAL_PATH_LOGIN);

    private Paths() {
    }
}

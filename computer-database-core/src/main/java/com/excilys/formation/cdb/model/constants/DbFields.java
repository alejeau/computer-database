package com.excilys.formation.cdb.model.constants;

import org.springframework.stereotype.Component;

@Component
public class DbFields {
    public static final String COMPUTER = "computer";
    public static final String COMPUTER_ID = "computer_id";
    public static final String COMPUTER_NAME = "computer_name";
    public static final String COMPUTER_INTRODUCED = "computer_introduced";
    public static final String COMPUTER_DISCONTINUED = "computer_discontinued";
    public static final String COMPUTER_COMPANY_ID = "computer_company_id";

    public static final String COMPANY = "company";
    public static final String COMPANY_ID = "company_id";
    public static final String COMPANY_NAME = "company_name";

    public static final String USER = "user";
    public static final String USER_USERNAME = "user_username";
    public static final String USER_PASSWORD = "user_password";
    public static final String USER_ROLE = "user_role";

    private DbFields() {
    }
}

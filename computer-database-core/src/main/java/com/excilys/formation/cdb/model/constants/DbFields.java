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
    public static final String COMPANY_PICTURE_URL = "company_picture_url";
    public static final String COMPANY_DESCRIPTION = "company_description";

    private DbFields() {
    }
}

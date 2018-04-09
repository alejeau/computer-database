package com.excilys.formation.cdb.persistence.dao.impl;

public class DbFields {
    private DbFields() {
    }

    public static final String COMPUTER = "computer";
    public static final String COMPUTER_ID = "computer_id";
    public static final String COMPUTER_NAME = "computer_name";
    public static final String COMPUTER_INTRODUCED = "computer_introduced";
    public static final String COMPUTER_DISCONTINUED = "computer_discontinued";
    public static final String COMPUTER_COMPANY_ID = "computer_company_id";
    public static final String COMPUTER_STAR = COMPUTER_ID + ", " + COMPUTER_NAME + ", " + COMPUTER_INTRODUCED + ", " + COMPUTER_DISCONTINUED + ", " + COMPUTER_COMPANY_ID;

    public static final String COMPANY = "company";
    public static final String COMPANY_ID = "company_id";
    public static final String COMPANY_NAME = "company_name";
    public static final String COMPANY_STAR = COMPANY_ID + ", " + COMPANY_NAME;

    public static final String COMPUTER_AND_COMPANY_STAR = COMPUTER_STAR + ", " + COMPANY_STAR;


}

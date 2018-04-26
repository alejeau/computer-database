package com.excilys.formation.cdb.validators.core;

public enum FieldName {
    COMPUTER_NAME,
    COMPUTER_INTRODUCED,
    COMPUTER_DISCONTINUED,
    COMPUTER_DATES,
    COMPANY_ID;

    public static String toModelDTOName(FieldName fieldName) {
        switch (fieldName) {
            case COMPUTER_NAME:
                return "name";
            case COMPUTER_INTRODUCED:
                return "introduced";
            case COMPUTER_DISCONTINUED:
                return "discontinued";
            case COMPANY_ID:
                return "companyId";
            default:
                return null;
        }
    }

    public static FieldName toFieldName(String s) {
        switch (s) {
            case "name":
                return COMPUTER_NAME;
            case "introduced":
                return COMPUTER_INTRODUCED;
            case "discontinued":
                return COMPUTER_DISCONTINUED;
            case "companyId":
                return COMPANY_ID;
            default:
                return null;
        }
    }
}

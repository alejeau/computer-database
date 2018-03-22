package com.excilys.formation.cdb.validators;

import com.excilys.formation.cdb.exceptions.DateException;
import com.excilys.formation.cdb.exceptions.ValidationException;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.DatePattern;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public enum ComputerValidator {
    INSTANCE;

    ComputerValidator() {

    }

    public void validate(Computer c) throws ValidationException {
        if (c.getIntroduced() != null && c.getDiscontinued() != null) {
            if (c.getIntroduced().isAfter(c.getDiscontinued())) {
                throw new DateException("The date of introduction cannot be after the discontinuation date.");
            }
        }
    }

    public List<Error> validate(String name, String introduced, String discontinued) {

        return null;
    }

    /**
     * Return an Error with the corresponding message if the name is null, empty, or starts with a ' ' character.
     *
     * @param name the computer's name
     * @return An Error in there is one, null otherwise
     */
    public Error validateName(String name) {
        if (name == null) {
            return new Error(Field.COMPUTER_NAME, ErrorMessage.CANT_BE_NULL);
        } else if (name.equals("") || name.isEmpty()) {
            return new Error(Field.COMPUTER_NAME, ErrorMessage.CANT_BE_EMPTY);
        } else if (name.startsWith(" ")) {
            return new Error(Field.COMPUTER_NAME, ErrorMessage.CANT_START_WITH_SPACE);
        }

        return null;
    }

    public static List<Error> validateDates(String introduced, String discontinued) {
        List<Error> errorList = null;
        Error e1 = null;
        Error e2 = null;

        e1 = validateDate(Field.COMPUTER_INTRODUCED, introduced);
        e2 = validateDate(Field.COMPUTER_DISCONTINUED, discontinued);
        errorList = addToList(errorList, e1);
        errorList = addToList(errorList, e2);

        if (e1 != null && e2 != null && introduced.compareTo(discontinued) >= 0) {
            errorList = addToList(errorList, new Error(Field.COMPUTER_DATES, ErrorMessage.INTRO_DATE_MUST_BE_BEFORE_DISCONTINUATION_DATE));
        }

        return errorList;
    }

    /**
     * Checks whether a String can be converted to a LocalDate
     *
     * @param field Field.COMPUTER_INTRODUCED or Field.COMPUTER_DISCONTINUED
     * @param date  the String to evaluate
     * @return null if valid date, an Error otherwise
     */
    public static Error validateDate(Field field, String date) {
        if (date != null) {
            if (!date.equals("")) {
                // If the date ain't correctly formatted
                try {
                    LocalDate.parse(date, DatePattern.FORMATTER);
                } catch (DateTimeParseException e) {
                    return new Error(field, ErrorMessage.MUST_FOLLOW_DATE_PATTERN);
                }
            }
        }

        return null;
    }

    protected static List<Error> addToList(List<Error> list, Error p) {
        if (p != null) {
            if (list == null) {
                list = new ArrayList<Error>();
            }
            list.add(p);
        }
        return list;
    }

    protected static List<Error> addToList(List<Error> list, List<Error> listToAdd) {
        if (listToAdd != null) {
            if (list == null) {
                list = new ArrayList<Error>();
            }
            list.addAll(listToAdd);
        }
        return list;
    }

    public static void main(String[] args) {
    }
}

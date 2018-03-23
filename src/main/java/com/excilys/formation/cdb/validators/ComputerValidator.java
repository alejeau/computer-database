package com.excilys.formation.cdb.validators;

import com.excilys.formation.cdb.exceptions.DateException;
import com.excilys.formation.cdb.exceptions.ValidationException;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.DatePattern;
import com.excilys.formation.cdb.validators.core.Error;
import com.excilys.formation.cdb.validators.core.ErrorMessage;
import com.excilys.formation.cdb.validators.core.FieldName;

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
        List<Error> errorList = null;
        errorList = addToList(errorList, validateName(name));
        errorList = addToList(errorList, validateDates(introduced, discontinued));
        return errorList;
    }

    /**
     * Return an Error with the corresponding message if the name is null, empty, or starts with a ' ' character.
     *
     * @param name the computer's name
     * @return An Error in there is one, null otherwise
     */
    public Error validateName(String name) {
        if (name == null) {
            return new Error(FieldName.COMPUTER_NAME, ErrorMessage.CANT_BE_NULL);
        } else if (name.equals("") || name.isEmpty()) {
            return new Error(FieldName.COMPUTER_NAME, ErrorMessage.CANT_BE_EMPTY);
        } else if (name.startsWith(" ")) {
            return new Error(FieldName.COMPUTER_NAME, ErrorMessage.CANT_START_WITH_SPACE);
        }

        return null;
    }

    /**
     * Checks whether the Strings are parsable LocalDates, and if introduced < discontinued.
     *
     * @param introduced   the introduction date
     * @param discontinued the discontinuation date
     * @return null if no errors, a list of Error otherwise
     */
    public List<Error> validateDates(String introduced, String discontinued) {
        List<Error> errorList = null;
        Error e1;
        Error e2;

        e1 = validateDate(FieldName.COMPUTER_INTRODUCED, introduced);
        e2 = validateDate(FieldName.COMPUTER_DISCONTINUED, discontinued);

        errorList = addToList(errorList, e1);
        errorList = addToList(errorList, e2);

        // if no errors and both dates aren't null, we can check their validity
        if (e1 == null
                && e2 == null
                && introduced != null
                && !introduced.isEmpty()
                && discontinued != null
                && !discontinued.isEmpty()
                && (introduced.compareTo(discontinued) >= 0)) {
            errorList = addToList(errorList, new Error(FieldName.COMPUTER_DATES, ErrorMessage.INTRO_DATE_MUST_BE_BEFORE_DISCONTINUATION_DATE));
        }

        return errorList;
    }

    /**
     * Checks whether a String can be converted to a LocalDate
     *
     * @param fieldName FieldName.COMPUTER_INTRODUCED or FieldName.COMPUTER_DISCONTINUED
     * @param date  the String to evaluate
     * @return null if valid date, an Error otherwise
     */
    public Error validateDate(FieldName fieldName, String date) {
        if (date != null) {
            if (!date.equals("")) {
                // If the date ain't correctly formatted
                try {
                    LocalDate.parse(date, DatePattern.FORMATTER);
                } catch (DateTimeParseException e) {
                    return new Error(fieldName, ErrorMessage.MUST_FOLLOW_DATE_PATTERN);
                }
            }
        }

        return null;
    }

    protected List<Error> addToList(List<Error> list, Error p) {
        if (p != null) {
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(p);
        }
        return list;
    }

    protected List<Error> addToList(List<Error> list, List<Error> listToAdd) {
        if (listToAdd != null) {
            if (list == null) {
                list = new ArrayList<>();
            }
            list.addAll(listToAdd);
        }
        return list;
    }
}

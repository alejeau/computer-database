package com.excilys.formation.cdb.validators;

import org.junit.Test;

import static org.junit.Assert.*;

public class ComputerValidatorTest {

    private static final String GOOD_NAME = "test";
    private static final String EMPTY_NAME = "";
    private static final String SPACE_NAME = " test";

    private static final String GOOD_DATE1 = "2018-03-21";
    private static final String GOOD_DATE2 = "2018-03-22";
    private static final String EMPTY_DATE = "";
    private static final String BAD_DATE1 = "2018-13-22";
    private static final String BAD_DATE2 = "2018-03-35";
    private static final String BAD_DATE3 = "vcidbqciouqb";

    private static final Error ERROR_NULL_NAME = new Error(Field.COMPUTER_NAME, ErrorMessage.CANT_BE_NULL);
    private static final Error ERROR_EMPTY_NAME = new Error(Field.COMPUTER_NAME, ErrorMessage.CANT_BE_EMPTY);
    private static final Error ERROR_SPACE = new Error(Field.COMPUTER_NAME, ErrorMessage.CANT_START_WITH_SPACE);

    private static final Error ERROR_DATE_PATTERN = new Error(Field.COMPUTER_DATES, ErrorMessage.MUST_FOLLOW_DATE_PATTERN);

    @Test
    public void validate() {
        assertNull(ComputerValidator.INSTANCE.validate(GOOD_NAME, GOOD_DATE1, GOOD_DATE2));
        assertNotNull(ComputerValidator.INSTANCE.validate(EMPTY_NAME, GOOD_DATE1, GOOD_DATE2));
        assertNotNull(ComputerValidator.INSTANCE.validate(GOOD_NAME, BAD_DATE1, GOOD_DATE2));
        assertNotNull(ComputerValidator.INSTANCE.validate(GOOD_NAME, GOOD_DATE1, BAD_DATE2));
        assertNotNull(ComputerValidator.INSTANCE.validate(GOOD_NAME, GOOD_DATE2, GOOD_DATE1));
    }

    @Test
    public void validateName() {
        assertNull(ComputerValidator.INSTANCE.validateName(GOOD_NAME));
        assertEquals(ComputerValidator.INSTANCE.validateName(null), ERROR_NULL_NAME);
        assertEquals(ComputerValidator.INSTANCE.validateName(EMPTY_NAME), ERROR_EMPTY_NAME);
        assertEquals(ComputerValidator.INSTANCE.validateName(SPACE_NAME), ERROR_SPACE);
    }

    @Test
    public void validateDates() {
        assertNull(ComputerValidator.INSTANCE.validateDates(GOOD_DATE1, GOOD_DATE2));
        assertNotNull(ComputerValidator.INSTANCE.validateDates(GOOD_DATE1, BAD_DATE1));
        assertNotNull(ComputerValidator.INSTANCE.validateDates(GOOD_DATE2, GOOD_DATE1));
    }

    @Test
    public void validateDate() {
        assertNull(ComputerValidator.INSTANCE.validateDate(Field.COMPUTER_DATES, GOOD_DATE1));
        assertNull(ComputerValidator.INSTANCE.validateDate(Field.COMPUTER_DATES, EMPTY_DATE));
        assertEquals(ComputerValidator.INSTANCE.validateDate(Field.COMPUTER_DATES, BAD_DATE1), ERROR_DATE_PATTERN);
        assertEquals(ComputerValidator.INSTANCE.validateDate(Field.COMPUTER_DATES, BAD_DATE2), ERROR_DATE_PATTERN);
        assertEquals(ComputerValidator.INSTANCE.validateDate(Field.COMPUTER_DATES, BAD_DATE3), ERROR_DATE_PATTERN);
    }
}
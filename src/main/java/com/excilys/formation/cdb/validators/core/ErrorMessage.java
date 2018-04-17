package com.excilys.formation.cdb.validators.core;

import com.excilys.formation.cdb.model.DatePattern;

public class ErrorMessage {
    private ErrorMessage() {
    }

    public static final String CANT_BE_NULL = "can't be null!";
    public static final String CANT_BE_EMPTY = "can't be empty!";
    public static final String CANT_START_WITH_SPACE = "can't start with space (' ') char !";
    public static final String MUST_FOLLOW_DATE_PATTERN = "must follow the pattern \"" + DatePattern.PATTERN + "\"!";
    public static final String INTRO_DATE_MUST_BE_BEFORE_DISCONTINUATION_DATE = "the date of introduction must be set before the discontinuation date!";
}

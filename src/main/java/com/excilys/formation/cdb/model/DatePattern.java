package com.excilys.formation.cdb.model;

import java.time.format.DateTimeFormatter;

public class DatePattern {
    DatePattern() {
    }

    public static final String PATTERN = "yyyy-MM-dd";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(PATTERN);
}

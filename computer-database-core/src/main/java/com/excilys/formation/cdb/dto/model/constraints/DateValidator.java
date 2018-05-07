package com.excilys.formation.cdb.dto.model.constraints;

import com.excilys.formation.cdb.model.DatePattern;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class DateValidator implements ConstraintValidator<DateConstraints, Object> {

    private String date1;
    private String date2;

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        boolean isDate1Valid = validateDate(date1);
        boolean isDate2Valid = validateDate(date2);

        return isDate1Valid
                && isDate2Valid
                && !StringUtils.isBlank(date1)
                && !StringUtils.isBlank(date2)
                && date1.compareTo(date2) >= 0;
    }

    @Override
    public void initialize(DateConstraints constraintAnnotation) {
        this.date1 = constraintAnnotation.date1();
        this.date2 = constraintAnnotation.date2();
    }

    private static boolean validateDate(String date) {
        if (!StringUtils.isBlank(date)) {
            // If the date ain't correctly formatted
            try {
                LocalDate.parse(date, DatePattern.FORMATTER);
            } catch (DateTimeParseException e) {
                return false;
            }

        }

        return true;
    }
}

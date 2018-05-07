package com.excilys.formation.cdb.dto.model.constraints;


import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<DateConstraints, String> {
    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        return !StringUtils.isBlank(name) && !name.startsWith(" ");
    }

    @Override
    public void initialize(DateConstraints constraintAnnotation) {

    }
}

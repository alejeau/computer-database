package com.excilys.formation.cdb.validators;

import com.excilys.formation.cdb.exceptions.DateException;
import com.excilys.formation.cdb.exceptions.MissingElementException;
import com.excilys.formation.cdb.exceptions.ValidationException;
import com.excilys.formation.cdb.model.Computer;

public enum ComputerValidator {
    INSTANCE;

    private ComputerValidator() {

    }

    public static void validate(Computer c) throws ValidationException {
        if (c.getIntroduced().isAfter(c.getDiscontinued()))
            throw new DateException("The date of introduction cannot be after the discontinuation date.");
        if (c.getCompanyId() == null)
            throw new MissingElementException("You have to specify a company!");
    }
}

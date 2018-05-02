package com.excilys.formation.cdb.service.validators;

import com.excilys.formation.cdb.dto.model.ComputerDTO;
import com.excilys.formation.cdb.service.validators.core.Error;
import com.excilys.formation.cdb.service.validators.core.FieldName;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class ComputerDTOValidator implements Validator {
    @Override
    public boolean supports(Class<?> paramClass) {
        return ComputerDTO.class.equals(paramClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ComputerDTO computerDTO = (ComputerDTO) o;
        List<Error> errorList = ComputerValidator.validate(computerDTO);
        if (errorList != null) {
            errorList.forEach(e -> errors.rejectValue(FieldName.toModelDTOName(e.getFieldName()), e.getMessage()));
        }
    }
}

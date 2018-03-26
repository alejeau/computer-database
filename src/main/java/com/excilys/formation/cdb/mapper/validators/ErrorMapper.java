package com.excilys.formation.cdb.mapper.validators;

import com.excilys.formation.cdb.validators.core.Error;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ErrorMapper {
    public static HashMap<String, String> toHashMap(List<Error> errorList) {
        HashMap<String, String> errorHashMap = new HashMap<>();

        if (errorList != null) {
            errorList.stream()
                    .filter(Objects::nonNull)
                    .forEach(error -> errorHashMap.put(error.getFieldName().toString(), error.getMessage()));
        }

        return errorHashMap;
    }
}
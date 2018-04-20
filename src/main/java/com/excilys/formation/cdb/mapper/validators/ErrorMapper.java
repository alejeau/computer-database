package com.excilys.formation.cdb.mapper.validators;

import com.excilys.formation.cdb.validators.core.Error;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class ErrorMapper {
    public static Map<String, String> toHashMap(List<Error> errorList) {
        HashMap<String, String> errorHashMap = new HashMap<>();

        if (errorList != null) {
            errorList.stream()
                    .filter(Objects::nonNull)
                    .forEach(error -> errorHashMap.put(error.getFieldName().toString(), error.getMessage()));
        }

        return errorHashMap;
    }
}

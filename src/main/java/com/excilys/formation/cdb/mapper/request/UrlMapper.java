package com.excilys.formation.cdb.mapper.request;

import com.excilys.formation.cdb.controllers.constants.ComputerField;
import com.excilys.formation.cdb.controllers.constants.ControllerParameters;
import com.excilys.formation.cdb.exceptions.UnauthorizedLimitValueException;
import com.excilys.formation.cdb.mapper.LimitValueMapper;
import com.excilys.formation.cdb.paginator.core.LimitValue;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UrlMapper {
    private static final Logger LOG = LoggerFactory.getLogger(UrlMapper.class);

    public static ComputerField mapToComputerFields(Map<String, String> params, String field, ComputerField defaultValue) {
        LOG.debug("mapToComputerFields");
        String stringField = params.get(field);
        for (ComputerField computerField : ComputerField.values()) {
            if (computerField.getValue().equals(stringField)) {
                return computerField;
            }
        }

        return defaultValue;
    }

    public static boolean mapToBoolean(Map<String, String> params, String field, boolean defaultValue) {
        LOG.debug("mapToBoolean");
        String stringField = params.get(field);

        if (!StringUtils.isBlank(stringField)) {
            if (stringField.equals("true")) {
                return true;
            }
            if (stringField.equals("false")) {
                return false;
            }
        }

        return defaultValue;
    }

    public static Long mapLongNumber(Map<String, String> params, String field, Long defaultValue) {
        LOG.debug("mapLongNumber");
        String stringLong = params.get(field);
        Long value = defaultValue;

        if ((stringLong != null) && !stringLong.isEmpty() && stringLong.matches("[0-9]+")) {
            value = Long.parseLong(stringLong);
        } else {
            LOG.error("Can't parse '{}' as a Long!", stringLong);
        }

        return value;
    }

    public static LimitValue mapDisplayBy(Map<String, String> params, LimitValue defaultValue) {
        LOG.debug("mapDisplayBy");
        String stringDisplayBy = params.get(ControllerParameters.DISPLAY_BY);

        if ((stringDisplayBy != null) && !stringDisplayBy.isEmpty() && stringDisplayBy.matches("[0-9]+")) {
            try {
                return LimitValueMapper.toLimitValue(stringDisplayBy);
            } catch (UnauthorizedLimitValueException e) {
                LOG.error("{}", e);
            }
        }

        return defaultValue;
    }
}

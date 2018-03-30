package com.excilys.formation.cdb.mapper.request;

import com.excilys.formation.cdb.exceptions.UnauthorizedLimitValueException;
import com.excilys.formation.cdb.mapper.LimitValueMapper;
import com.excilys.formation.cdb.paginator.core.LimitValue;
import com.excilys.formation.cdb.servlets.constants.ComputerField;
import com.excilys.formation.cdb.servlets.constants.ServletParameter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class UrlMapper {
    private static final Logger LOG = LoggerFactory.getLogger(UrlMapper.class);

    private UrlMapper() {
    }

    public static ComputerField mapToComputerFields(HttpServletRequest request, String field, ComputerField defaultValue) {
        LOG.debug("mapToComputerFields");
        String stringField = request.getParameter(field);
        for (ComputerField computerField : ComputerField.values()) {
            if (computerField.getValue().equals(stringField)) {
                return computerField;
            }
        }

        return defaultValue;
    }

    public static boolean mapToBoolean(HttpServletRequest request, String field, boolean defaultValue) {
        LOG.debug("mapToBoolean");
        String stringField = request.getParameter(field);

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

    public static Long mapLongNumber(HttpServletRequest request, String field, Long defaultValue) {
        LOG.debug("mapLongNumber");
        String stringLong = request.getParameter(field);
        Long value = defaultValue;

        if ((stringLong != null) && !stringLong.isEmpty() && stringLong.matches("[0-9]+")) {
            value = Long.parseLong(stringLong);
        } else {
            LOG.error("Can't parse '{}' as a Long!", stringLong);
        }

        return value;
    }

    /**
     * Maps a HttpServletRequest to a {@link LimitValue}
     *
     * @param request      the servlet request containing the parameter {@link ServletParameter}.DISPLAY_BY
     * @param defaultValue the default {@link LimitValue} to return if an error occurs
     * @return the mapped {@link LimitValue} of the defaultValue
     */
    public static LimitValue mapDisplayBy(HttpServletRequest request, LimitValue defaultValue) {
        LOG.debug("mapDisplayBy");
        String stringDisplayBy = request.getParameter(ServletParameter.DISPLAY_BY);

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

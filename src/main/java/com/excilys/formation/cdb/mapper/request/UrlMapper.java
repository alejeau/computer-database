package com.excilys.formation.cdb.mapper.request;

import com.excilys.formation.cdb.exceptions.UnauthorizedLimitValueException;
import com.excilys.formation.cdb.mapper.LimitValueMapper;
import com.excilys.formation.cdb.paginator.core.LimitValue;
import com.excilys.formation.cdb.paginator.core.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class UrlMapper {

    private static final Logger LOG = LoggerFactory.getLogger(UrlMapper.class);

    public static Long mapLongNumber(HttpServletRequest request, String field, Long defaultValue) {
        String stringLong = request.getParameter(field);
        LOG.debug("stringLong: {}", stringLong);
        Long value = defaultValue;

        if ((stringLong != null) && !stringLong.isEmpty() && stringLong.matches("[0-9]+")) {
            value = Long.parseLong(stringLong);
        } else {
            LOG.error("Can't parse '" + stringLong + "' as a Long!");
        }

        return value;
    }


    public static LimitValue mapDisplayBy(HttpServletRequest request) {
        String stringDisplayBy = request.getParameter(UrlFields.DISPLAY_BY);

        LimitValue displayBy = LimitValue.TEN;

        if ((stringDisplayBy != null) && !stringDisplayBy.isEmpty() && stringDisplayBy.matches("[0-9]+")) {
            try {
                displayBy = LimitValueMapper.toLimitValue(stringDisplayBy);
            } catch (UnauthorizedLimitValueException e) {
                LOG.error(e.getMessage());
            }
        }

        return displayBy;
    }
}

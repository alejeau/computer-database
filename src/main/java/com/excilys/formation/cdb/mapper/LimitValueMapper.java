package com.excilys.formation.cdb.mapper;

import com.excilys.formation.cdb.exceptions.UnauthorizedLimitValueException;
import com.excilys.formation.cdb.paginator.core.LimitValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LimitValueMapper {
    private static final Logger LOG = LoggerFactory.getLogger(LimitValueMapper.class);

    public static LimitValue toLimitValue(Long value) throws UnauthorizedLimitValueException {
        for (LimitValue val : LimitValue.values()) {
            if (value.equals(val.getValue())) {
                return val;
            }
        }
        throw new UnauthorizedLimitValueException(value + " is not a valid limit value");
    }

    public static LimitValue toLimitValue(String value) throws UnauthorizedLimitValueException {
        if (value != null) {
            Long tmp = Long.parseLong(value);
            for (LimitValue val : LimitValue.values()) {
                if (tmp.equals(val.getValue())) {
                    return val;
                }
            }
        }
        throw new UnauthorizedLimitValueException(value + " is not a valid limit value");
    }
}

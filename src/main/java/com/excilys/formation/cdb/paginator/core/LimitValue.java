package com.excilys.formation.cdb.paginator.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum LimitValue {
    TEN(10L),
    TWENTY(20L),
    FIFTY(50L),
    ONE_HUNDRED(100L);

    private final Long value;

    LimitValue(final Long newValue) {
        value = newValue;
    }

    public Long getValue() {
        return value;
    }

    /**
     * Returns a sorted List of the values of the Enums.
     *
     * @return a sorted List
     */
    public static List<Long> toLongList() {
        List<Long> longList = new ArrayList<>(LimitValue.values().length);
        Arrays.stream(LimitValue.values())
                .map(LimitValue::getValue)
                .forEach(longList::add);
        Collections.sort(longList);
        return longList;
    }
}

package com.excilys.formation.cdb.paginator.core;

public enum LimitValue {
    TEN(10l),
    TWENTY(20l),
    ONE_HUNDRED(100l);

    private final Long value;

    LimitValue(final Long newValue) {
        value = newValue;
    }

    public Long getValue() {
        return value;
    }
}

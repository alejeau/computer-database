package com.excilys.formation.cdb.paginator.core;

public enum LIMIT_VALUE {
    TEN(10),
    TWENTY(20),
    ONE_HUNDRED(100);

    private final int value;

    LIMIT_VALUE(final int newValue) {
        value = newValue;
    }

    public int getValue() { return value; }

}

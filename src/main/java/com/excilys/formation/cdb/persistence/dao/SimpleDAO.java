package com.excilys.formation.cdb.persistence.dao;

public interface SimpleDAO {

    public abstract Long count(String query);
    public abstract Long countElementsWithName(String query, String name);
}

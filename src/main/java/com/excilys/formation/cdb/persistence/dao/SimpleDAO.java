package com.excilys.formation.cdb.persistence.dao;

public interface SimpleDAO {

    Long count(String query);

    Long countElementsWithName(String query, String name);
}

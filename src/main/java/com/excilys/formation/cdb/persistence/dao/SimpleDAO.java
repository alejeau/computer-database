package com.excilys.formation.cdb.persistence.dao;

import com.excilys.formation.cdb.exceptions.DAOException;

public interface SimpleDAO {

    Long count(String query) throws DAOException;

    Long countElementsWithName(String query, String name) throws DAOException;
}

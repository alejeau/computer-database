package com.excilys.formation.cdb.persistence.dao;

import com.excilys.formation.cdb.exceptions.DAOException;

import java.util.List;

public interface SimpleDAO {

    Long count(String query) throws DAOException;

    Long countElementsWithName(String query, String name) throws DAOException;

    Long countWithStringParameters(String query, List<String> params) throws DAOException;
}

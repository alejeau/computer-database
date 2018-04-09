package com.excilys.formation.cdb.persistence.dao;

import com.excilys.formation.cdb.exceptions.DAOException;
import com.excilys.formation.cdb.model.Company;

import java.util.List;

public interface CompanyDAO {

    Long getNumberOfCompanies() throws DAOException;

    Long getNumberOfCompaniesWithName(String name) throws DAOException;

    Company getCompany(Long id) throws DAOException;

    List<Company> getCompaniesWithName(String name, Long index, Long limit) throws DAOException;

    List<Company> getCompanies() throws DAOException;

    List<Company> getCompanies(Long index, Long limit) throws DAOException;

    void deleteCompany(Long id) throws DAOException;
}

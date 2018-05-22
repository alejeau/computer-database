package com.excilys.formation.cdb.persistence.dao;

import com.excilys.formation.cdb.exceptions.DAOException;
import com.excilys.formation.cdb.model.Company;

import java.util.List;

public interface CompanyDAO {

    Long getNumberOfCompanies() throws DAOException;

    Long getNumberOfCompaniesWithName(String name) throws DAOException;

    Company getCompany(Long id) throws DAOException;

    List<Company> getCompanyList() throws DAOException;

    List<Company> getCompanyList(Long index, Long limit) throws DAOException;

    List<Company> getCompaniesWithName(String name, Long index, Long limit) throws DAOException;

    Long persistCompany(Company company) throws DAOException;

    void updateCompany(Company company) throws DAOException;

    void deleteCompany(Long id) throws DAOException;
}

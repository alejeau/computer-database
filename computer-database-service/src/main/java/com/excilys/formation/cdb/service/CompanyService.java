package com.excilys.formation.cdb.service;

import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.model.Company;

import java.util.List;

public interface CompanyService {

    Long getNumberOfCompanies() throws ServiceException;

    Company getCompany(Long id) throws ServiceException;

    List<Company> getCompanies() throws ServiceException;

    List<Company> getCompanies(Long index, Long limit) throws ServiceException;

    Long persistCompany(Company company) throws ServiceException;

    void updateCompany(Company company) throws ServiceException;

    void deleteCompany(Long id) throws ServiceException;
}

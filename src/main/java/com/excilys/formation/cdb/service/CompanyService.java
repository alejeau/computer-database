package com.excilys.formation.cdb.service;

import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.paginator.CompanyPage;
import com.excilys.formation.cdb.paginator.CompanySearchPage;
import com.excilys.formation.cdb.paginator.core.LimitValue;

import java.util.List;

public interface CompanyService {

    Long getNumberOfCompanies() throws ServiceException;

    Long getNumberOfCompaniesWithName(String name) throws ServiceException;

    Company getCompany(Long id) throws ServiceException;

    List<Company> getCompany(String name, Long index, Long limit) throws ServiceException;

    List<Company> getCompanies() throws ServiceException;

    List<Company> getCompanies(Long index, Long limit) throws ServiceException;

    public CompanyPage getCompanyPage(LimitValue limit);

    public CompanySearchPage getCompanySearchPage(String name, LimitValue limit);
}

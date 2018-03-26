package com.excilys.formation.cdb.service;

import com.excilys.formation.cdb.exceptions.DAOException;
import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.paginator.CompanyPage;
import com.excilys.formation.cdb.paginator.CompanySearchPage;
import com.excilys.formation.cdb.paginator.core.LimitValue;
import com.excilys.formation.cdb.persistence.dao.CompanyDAO;
import com.excilys.formation.cdb.persistence.dao.impl.CompanyDAOImpl;

import java.util.List;

public enum CompanyService implements CompanyDAO {
    INSTANCE;

    @Override
    public Long getNumberOfCompanies() throws ServiceException {
        try {
            return CompanyDAOImpl.INSTANCE.getNumberOfCompanies();
        } catch () {

        }
    }

    @Override
    public Long getNumberOfCompaniesWithName(String name) throws ServiceException {
        return CompanyDAOImpl.INSTANCE.getNumberOfCompaniesWithName(name);
    }

    @Override
    public Company getCompany(Long id) throws ServiceException {
        return CompanyDAOImpl.INSTANCE.getCompany(id);
    }

    @Override
    public List<Company> getCompany(String name, Long index, Long limit) throws ServiceException {
        return CompanyDAOImpl.INSTANCE.getCompany(name, index, limit);
    }

    @Override
    public List<Company> getCompanies() throws ServiceException {
        return CompanyDAOImpl.INSTANCE.getCompanies();
    }

    @Override
    public List<Company> getCompanies(Long index, Long limit) throws ServiceException {
        return CompanyDAOImpl.INSTANCE.getCompanies(index, limit);
    }

    public CompanyPage getCompanyPage(LimitValue limit) throws ServiceException {
        return new CompanyPage(limit);
    }

    public CompanySearchPage getCompanySearchPage(String name, LimitValue limit) throws ServiceException {
        return new CompanySearchPage(name, limit);
    }
}

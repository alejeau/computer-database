package com.excilys.formation.cdb.service;

import com.excilys.formation.cdb.exceptions.DAOException;
import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.paginator.CompanyPage;
import com.excilys.formation.cdb.paginator.CompanySearchPage;
import com.excilys.formation.cdb.paginator.core.LimitValue;
import com.excilys.formation.cdb.persistence.dao.CompanyDAO;
import com.excilys.formation.cdb.persistence.dao.impl.CompanyDAOImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public enum CompanyService implements CompanyDAO {
    INSTANCE;
    private static final Logger LOG = LoggerFactory.getLogger(CompanyService.class);

    @Override
    public Long getNumberOfCompanies() throws ServiceException {
        try {
            return CompanyDAOImpl.INSTANCE.getNumberOfCompanies();
        } catch (DAOException e) {
            LOG.error("{}", e);
            throw new ServiceException("Couldn't retrieve the number of companies!", e);
        }
    }

    @Override
    public Long getNumberOfCompaniesWithName(String name) throws ServiceException {
        try {
            return CompanyDAOImpl.INSTANCE.getNumberOfCompaniesWithName(name);
        } catch (DAOException e) {
            LOG.error("{}", e);
            throw new ServiceException("Couldn't retrieve the number of companies WITH NAME LIKE \"" + name + "\"!", e);
        }
    }

    @Override
    public Company getCompany(Long id) throws ServiceException {
        try {
            return CompanyDAOImpl.INSTANCE.getCompany(id);
        } catch (DAOException e) {
            LOG.error("{}", e);
            throw new ServiceException("Couldn't retrieve the the company with the \"" + id + "\"!", e);
        }
    }

    @Override
    public List<Company> getCompany(String name, Long index, Long limit) throws ServiceException {
        try {
            return CompanyDAOImpl.INSTANCE.getCompany(name, index, limit);
        } catch (DAOException e) {
            LOG.error("{}", e);
            throw new ServiceException("Couldn't retrieve the number of companies WITH NAME LIKE \"" + name + "\"!", e);
        }
    }

    @Override
    public List<Company> getCompanies() throws ServiceException {
        try {
            return CompanyDAOImpl.INSTANCE.getCompanies();
        } catch (DAOException e) {
            LOG.error("{}", e);
            throw new ServiceException("Couldn't retrieve the complete list of companies!", e);
        }
    }

    @Override
    public List<Company> getCompanies(Long index, Long limit) throws ServiceException {
        try {
            return CompanyDAOImpl.INSTANCE.getCompanies(index, limit);
        } catch (DAOException e) {
            LOG.error("{}", e);
            throw new ServiceException("Couldn't get list of companies from " + index + " to " + limit + "!", e);
        }
    }

    public CompanyPage getCompanyPage(LimitValue limit) {
        return new CompanyPage(limit);
    }

    public CompanySearchPage getCompanySearchPage(String name, LimitValue limit) {
        return new CompanySearchPage(name, limit);
    }
}

package com.excilys.formation.cdb.service.impl;

import com.excilys.formation.cdb.exceptions.DAOException;
import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.paginator.CompanyPage;
import com.excilys.formation.cdb.paginator.CompanySearchPage;
import com.excilys.formation.cdb.paginator.core.LimitValue;
import com.excilys.formation.cdb.persistence.dao.CompanyDAO;
import com.excilys.formation.cdb.persistence.dao.impl.CompanyDAOImpl;
import com.excilys.formation.cdb.service.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public enum CompanyServiceImpl implements CompanyService {
    INSTANCE;
    private static final Logger LOG = LoggerFactory.getLogger(CompanyServiceImpl.class);
    private static CompanyDAO companyDAO = CompanyDAOImpl.INSTANCE;

    @Override
    public Long getNumberOfCompanies() throws ServiceException {
        try {
            return companyDAO.getNumberOfCompanies();
        } catch (DAOException e) {
            LOG.error("{}", e);
            throw new ServiceException("Couldn't retrieve the number of companies!", e);
        }
    }

    @Override
    public Long getNumberOfCompaniesWithName(String name) throws ServiceException {
        try {
            return companyDAO.getNumberOfCompaniesWithName(name);
        } catch (DAOException e) {
            LOG.error("{}", e);
            throw new ServiceException("Couldn't retrieve the number of companies WITH NAME LIKE \"" + name + "\"!", e);
        }
    }

    @Override
    public Company getCompany(Long id) throws ServiceException {
        try {
            return companyDAO.getCompany(id);
        } catch (DAOException e) {
            LOG.error("{}", e);
            throw new ServiceException("Couldn't retrieve the the company with the \"" + id + "\"!", e);
        }
    }

    @Override
    public List<Company> getCompany(String name, Long index, Long limit) throws ServiceException {
        try {
            return companyDAO.getCompaniesWithName(name, index, limit);
        } catch (DAOException e) {
            LOG.error("{}", e);
            throw new ServiceException("Couldn't retrieve the number of companies WITH NAME LIKE \"" + name + "\"!", e);
        }
    }

    @Override
    public List<Company> getCompanies() throws ServiceException {
        try {
            return companyDAO.getCompanies();
        } catch (DAOException e) {
            LOG.error("{}", e);
            throw new ServiceException("Couldn't retrieve the complete list of companies!", e);
        }
    }

    @Override
    public List<Company> getCompanies(Long index, Long limit) throws ServiceException {
        try {
            return companyDAO.getCompanies(index, limit);
        } catch (DAOException e) {
            LOG.error("{}", e);
            throw new ServiceException("Couldn't get list of companies from " + index + " to " + limit + "!", e);
        }
    }

    @Override
    public void deleteCompany(Long id) throws ServiceException {
        try {
            companyDAO.deleteCompany(id);
        } catch (DAOException e) {
            LOG.error("{}", e);
            throw new ServiceException("Couldn't the company with ID " + id + "!", e);
        }
    }


    public CompanyPage getCompanyPage(LimitValue limit) {
        return new CompanyPage(limit);
    }

    public CompanySearchPage getCompanySearchPage(String name, LimitValue limit) {
        return new CompanySearchPage(name, limit);
    }
}

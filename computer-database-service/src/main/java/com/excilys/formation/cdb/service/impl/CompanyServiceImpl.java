package com.excilys.formation.cdb.service.impl;

import com.excilys.formation.cdb.exceptions.DAOException;
import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.persistence.dao.CompanyDAO;
import com.excilys.formation.cdb.service.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("CompanyService")
@EnableTransactionManagement
public class CompanyServiceImpl implements CompanyService {
    private static final Logger LOG = LoggerFactory.getLogger(CompanyServiceImpl.class);

    private CompanyDAO companyDAO;

    @Autowired
    public CompanyServiceImpl(CompanyDAO companyDAO) {
        this.companyDAO = companyDAO;
    }

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
            throw new ServiceException("Couldn't retrieve the number of companies with name \"" + name + "\"!", e);
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
    public List<Company> getCompanyList() throws ServiceException {
        try {
            return companyDAO.getCompanyList();
        } catch (DAOException e) {
            LOG.error("{}", e);
            throw new ServiceException("Couldn't retrieve the complete list of companies!", e);
        }
    }

    @Override
    public List<Company> getCompanyList(Long index, Long limit) throws ServiceException {
        try {
            return companyDAO.getCompanyList(index, limit);
        } catch (DAOException e) {
            LOG.error("{}", e);
            throw new ServiceException("Couldn't get list of companies from " + index + " to " + limit + "!", e);
        }
    }

    @Override
    public List<Company> getCompaniesWithName(String name, Long index, Long limit) throws ServiceException {
        try {
            return companyDAO.getCompaniesWithName(name, index, limit);
        } catch (DAOException e) {
            LOG.error("{}", e);
            throw new ServiceException("Couldn't get list of companies with name " + name + " from " + index + " to " + limit + "!", e);
        }
    }

    @Override
    public Long persistCompany(Company company) throws ServiceException {
        try {
            return companyDAO.persistCompany(company);
        } catch (DAOException e) {
            LOG.error("{}", e);
            throw new ServiceException("Couldn't persist the company!", e);
        }
    }

    @Override
    public void updateCompany(Company company) throws ServiceException {
        try {
            companyDAO.updateCompany(company);
        } catch (DAOException e) {
            LOG.error("{}", e);
            throw new ServiceException("Couldn't update the company!", e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCompany(Long id) throws ServiceException {
        try {
            companyDAO.deleteCompany(id);
        } catch (DAOException e) {
            LOG.error("{}", e);
            throw new ServiceException("Couldn't the company with ID " + id + "!", e);
        }
    }
}

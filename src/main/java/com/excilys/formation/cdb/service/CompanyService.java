package com.excilys.formation.cdb.service;

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
    public Long getNumberOfCompanies() {
        return CompanyDAOImpl.INSTANCE.getNumberOfCompanies();
    }

    @Override
    public Long getNumberOfCompaniesWithName(String name) {
        return CompanyDAOImpl.INSTANCE.getNumberOfCompaniesWithName(name);
    }

    @Override
    public Company getCompany(Long id) {
        return CompanyDAOImpl.INSTANCE.getCompany(id);
    }

    @Override
    public List<Company> getCompany(String name, Long index, Long limit) {
        return CompanyDAOImpl.INSTANCE.getCompany(name, index, limit);
    }

    @Override
    public List<Company> getCompanies() {
        return CompanyDAOImpl.INSTANCE.getCompanies();
    }

    @Override
    public List<Company> getCompanies(Long index, Long limit) {
        return CompanyDAOImpl.INSTANCE.getCompanies(index, limit);
    }

    public CompanyPage getCompanyPage(LimitValue limit) {
        return new CompanyPage(limit);
    }

    public CompanySearchPage getCompanySearchPage(String name, LimitValue limit) {
        return new CompanySearchPage(name, limit);
    }
}

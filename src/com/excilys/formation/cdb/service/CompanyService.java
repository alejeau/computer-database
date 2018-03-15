package com.excilys.formation.cdb.service;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.paginator.CompanyPage;
import com.excilys.formation.cdb.paginator.CompanySearchPage;
import com.excilys.formation.cdb.paginator.core.LIMIT_VALUE;
import com.excilys.formation.cdb.persistence.dao.impl.CompanyDAOImpl;

import java.util.List;

public enum CompanyService {
    INSTANCE;

    public Long getNumberOfCompanies() {
        return CompanyDAOImpl.INSTANCE.getNumberOfCompanies();
    }

    public Long getNumberOfCompaniesWithName(String name) {
        return CompanyDAOImpl.INSTANCE.getNumberOfCompaniesWithName(name);
    }

    public Company getCompany(Long id) {
        return CompanyDAOImpl.INSTANCE.getCompany(id);
    }

    public List<Company> getCompany(String name, int index, int limit) {
        return CompanyDAOImpl.INSTANCE.getCompany(name, index, limit);
    }

    public CompanyPage getCompanyPage(LIMIT_VALUE limit) {
        return new CompanyPage(limit);
    }

    public CompanySearchPage getCompanySearchPage(String name, LIMIT_VALUE limit) {
        return new CompanySearchPage(name, limit);
    }

    public List<Company> getCompanies(int index, int limit) {
        return CompanyDAOImpl.INSTANCE.getCompanies(index, limit);
    }
}

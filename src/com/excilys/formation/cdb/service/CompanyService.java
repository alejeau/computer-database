package com.excilys.formation.cdb.service;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.paginator.CompanyPage;
import com.excilys.formation.cdb.paginator.CompanySearchPage;
import com.excilys.formation.cdb.paginator.core.OFFSET_VALUE;
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

    public List<Company> getCompany(String name, int index, int offset) {
        return CompanyDAOImpl.INSTANCE.getCompany(name, index, offset);
    }

    public CompanyPage getCompanyPage(OFFSET_VALUE offset) {
        return new CompanyPage(offset);
    }

    public CompanySearchPage getCompanySearchPage(String name, OFFSET_VALUE offset) {
        return new CompanySearchPage(name, offset);
    }

    public List<Company> getCompanies(int index, int offset) {
        return CompanyDAOImpl.INSTANCE.getCompanies(index, offset);
    }
}

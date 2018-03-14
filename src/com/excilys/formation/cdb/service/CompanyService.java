package com.excilys.formation.cdb.service;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.persistence.dao.impl.CompanyDAOImpl;

import java.util.List;

public enum CompanyService {
    INSTANCE;

    public Long getNumberOfCompanies() {
        return CompanyDAOImpl.INSTANCE.getNumberOfCompanies();
    }

    public Company getCompany(Long id) {
        return CompanyDAOImpl.INSTANCE.getCompany(id);
    }

    public List<Company> getCompany(String name, int index, int offset) {
        return CompanyDAOImpl.INSTANCE.getCompany(name, index, offset);
    }

    public List<Company> getCompanies(int index, int offset) {
        return CompanyDAOImpl.INSTANCE.getCompanies(index, offset);
    }
}

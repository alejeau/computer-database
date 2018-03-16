package com.excilys.formation.cdb.persistence.dao;

import com.excilys.formation.cdb.model.Company;

import java.util.List;

public interface CompanyDAO {

    Long getNumberOfCompanies();

    Long getNumberOfCompaniesWithName(String name);

    Company getCompany(Long id);

    List<Company> getCompany(String name, int index, int limit);

    List<Company> getCompanies(int index, int limit);
}

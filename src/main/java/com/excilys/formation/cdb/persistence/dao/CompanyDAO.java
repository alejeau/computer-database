package com.excilys.formation.cdb.persistence.dao;

import com.excilys.formation.cdb.model.Company;

import java.util.List;

public interface CompanyDAO {

    Long getNumberOfCompanies();

    Long getNumberOfCompaniesWithName(String name);

    Company getCompany(Long id);

    List<Company> getCompany(String name, long index, Long limit);

    List<Company> getCompanies(long index, Long limit);
}

package com.excilys.formation.cdb.persistence.dao;

import java.util.List;

import com.excilys.formation.cdb.model.Company;

public interface CompanyDAO {
	
	public abstract Long getNumberOfCompanies();
	public abstract Long getNumberOfCompaniesWithName(String name);
	
	public abstract Company getCompany(Long id);
	public abstract List<Company> getCompany(String name, int index, int limit);
	public abstract List<Company> getCompanies(int index, int limit);

}

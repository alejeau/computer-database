package com.excilys.formation.cdb.persistance.dao;

import java.util.List;

import com.excilys.formation.cdb.model.Company;

public interface CompanyDAO {
	
	public abstract Long getNumberOfCompanies();
	
	public abstract Company getCompany(Long id);
	public abstract List<Company> getCompany(String name, int index, int offset);
	public abstract List<Company> getCompanies(int index, int stop);

}

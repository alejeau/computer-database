package com.excilys.formation.cdb.service;

import com.excilys.formation.cdb.exceptions.ServiceException;

public interface ComputerCompanyService {

    void deleteCompanyWithIdAndAssociatedComputers(Long id) throws ServiceException;
}

package com.excilys.formation.cdb.service;

import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.exceptions.ValidationException;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistence.DatabaseField;

import java.util.List;

public interface ComputerService {

    Long getNumberOfComputers() throws ServiceException;

    Long getNumberOfComputersWithName(String name) throws ServiceException;

    Computer getComputer(Long id) throws ServiceException;

    List<Computer> getComputer(String name, long index, Long limit) throws ServiceException;

    List<Computer> getComputerOrderedBy(String name, long index, Long limit, DatabaseField computerField, boolean ascending) throws ServiceException;

    List<Computer> getComputerList(long index, Long limit) throws ServiceException;

    List<Computer> getComputerListOrderedBy(long index, Long limit, DatabaseField computerField, boolean ascending) throws ServiceException;

    List<Computer> getComputerListWithCompanyId(Long companyId) throws ServiceException;

    void updateComputer(Computer c) throws ValidationException, ServiceException;

    Long persistComputer(Computer c) throws ValidationException, ServiceException;

    void deleteComputer(Long id) throws ServiceException;

    void deleteComputers(List<Long> idList) throws ServiceException;

    void deleteComputersWithCompanyId(Long companyId) throws ServiceException;
}

package com.excilys.formation.cdb.persistence.dao;

import com.excilys.formation.cdb.exceptions.DAOException;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistence.DatabaseField;

import java.util.List;

public interface ComputerDAO {

    Long getNumberOfComputers() throws DAOException;

    Long getNumberOfComputersWithName(String name) throws DAOException;

    Computer getComputer(Long id) throws DAOException;

    List<Computer> getComputersWithName(String name, long index, Long limit) throws DAOException;

    List<Computer> getComputersWithNameOrderedBy(String name, long index, Long limit, DatabaseField computerField, boolean ascending) throws DAOException;

    List<Computer> getComputerList(long index, Long limit) throws DAOException;

    List<Computer> getComputerListOrderedBy(long index, Long limit, DatabaseField computerField, boolean ascending) throws DAOException;

    List<Computer> getComputerListWithCompanyId(Long id) throws DAOException;

    void updateComputer(Computer c) throws DAOException;

    Long persistComputer(Computer c) throws DAOException;

    void deleteComputer(Long id) throws DAOException;

    void deleteComputers(List<Long> idList) throws DAOException;

    void deleteComputersWithCompanyId(Long companyId) throws DAOException;
}

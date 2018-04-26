package com.excilys.formation.cdb.persistence.dao.impl;

import com.excilys.formation.cdb.exceptions.DAOException;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistence.DatabaseField;
import com.excilys.formation.cdb.persistence.dao.ComputerDAO;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ComputerDaoUnimpl implements ComputerDAO {
    @Override
    public Long getNumberOfComputers() throws DAOException {
        throw new NotYetImplementedException();
    }

    @Override
    public Long getNumberOfComputersWithName(String name) throws DAOException {
        throw new NotYetImplementedException();
    }

    @Override
    public Computer getComputer(Long id) throws DAOException {
        throw new NotYetImplementedException();
    }

    @Override
    public List<Computer> getComputersWithName(String name, long index, Long limit) throws DAOException {
        throw new NotYetImplementedException();
    }

    @Override
    public List<Computer> getComputersWithNameOrderedBy(String name, long index, Long limit, DatabaseField computerField, boolean ascending) throws DAOException {
        throw new NotYetImplementedException();
    }

    @Override
    public List<Computer> getComputerList(long index, Long limit) throws DAOException {
        throw new NotYetImplementedException();
    }

    @Override
    public List<Computer> getComputerListOrderedBy(long index, Long limit, DatabaseField computerField, boolean ascending) throws DAOException {
        throw new NotYetImplementedException();
    }

    @Override
    public void updateComputer(Computer c) throws DAOException {
        throw new NotYetImplementedException();
    }

    @Override
    public Long persistComputer(Computer c) throws DAOException {
        throw new NotYetImplementedException();
    }

    @Override
    public void deleteComputer(Long id) throws DAOException {
        throw new NotYetImplementedException();
    }

    @Override
    public void deleteComputers(List<Long> idList) throws DAOException {
        throw new NotYetImplementedException();
    }

    @Override
    public void deleteComputersWhitCompanyId(Long companyId) throws DAOException {
        throw new NotYetImplementedException();
    }
}

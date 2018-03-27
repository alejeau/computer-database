package com.excilys.formation.cdb.service;

import com.excilys.formation.cdb.exceptions.DAOException;
import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.exceptions.ValidationException;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.paginator.ComputerPage;
import com.excilys.formation.cdb.paginator.ComputerSearchPage;
import com.excilys.formation.cdb.paginator.core.LimitValue;
import com.excilys.formation.cdb.persistence.dao.impl.ComputerDAOImpl;
import com.excilys.formation.cdb.validators.ComputerValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public enum ComputerService {
    INSTANCE;
    private static final Logger LOG = LoggerFactory.getLogger(ComputerService.class);

    ComputerService() {

    }

    public Long getNumberOfComputers() throws ServiceException {
        try {
            return ComputerDAOImpl.INSTANCE.getNumberOfComputers();
        } catch (DAOException e) {
            LOG.error("{}", e);
            throw new ServiceException("Couldn't retrieve the number of computers!", e);
        }
    }

    public Long getNumberOfComputersWithName(String name) throws ServiceException {
        try {
            return ComputerDAOImpl.INSTANCE.getNumberOfComputersWithName(name);
        } catch (DAOException e) {
            LOG.error("{}", e);
            throw new ServiceException("Couldn't retrieve the number of computers WITH NAME LIKE \"" + name + "\"!", e);
        }
    }

    public Computer getComputer(Long id) throws ServiceException {
        try {
            return ComputerDAOImpl.INSTANCE.getComputer(id);
        } catch (DAOException e) {
            LOG.error("{}", e);
            throw new ServiceException("Couldn't retrieve computer with ID " + id + "!", e);
        }
    }

    public List<Computer> getComputer(String name, long index, Long limit) throws ServiceException {
        try {
            return ComputerDAOImpl.INSTANCE.getComputer(name, index, limit);
        } catch (DAOException e) {
            LOG.error("{}", e);
            throw new ServiceException("Couldn't retrieve the number of computers WITH NAME LIKE \"" + name + "\"!", e);
        }
    }

    public ComputerSearchPage getComputer(String name, LimitValue limit) throws ServiceException {
        return new ComputerSearchPage(name, limit);
    }

    public ComputerPage getComputers(LimitValue limit) throws ServiceException {
        return new ComputerPage(limit);
    }

    public List<Computer> getComputers(long index, Long limit) throws ServiceException {
        try {
            return ComputerDAOImpl.INSTANCE.getComputers(index, limit);
        } catch (DAOException e) {
            LOG.error("{}", e);
            throw new ServiceException("Couldn't get list of computers from " + index + " to " + limit + "!", e);
        }
    }

    public Long persistComputer(Computer c) throws ValidationException, ServiceException {
        ComputerValidator.INSTANCE.validate(c);
        try {
            return ComputerDAOImpl.INSTANCE.persistComputer(c);
        } catch (DAOException e) {
            LOG.error("{}", e);
            throw new ServiceException("Couldn't store the computer!", e);
        }
    }

    public void updateComputer(Computer c) throws ValidationException, ServiceException {
        ComputerValidator.INSTANCE.validate(c);
        try {
            ComputerDAOImpl.INSTANCE.updateComputer(c);
        } catch (DAOException e) {
            LOG.error("{}", e);
            throw new ServiceException("Couldn't update the computer!", e);
        }
    }

    public void deleteComputer(Long id) throws ServiceException {
        try {
            ComputerDAOImpl.INSTANCE.deleteComputer(id);
        } catch (DAOException e) {
            LOG.error("{}", e);
            throw new ServiceException("Couldn't delete the computer with ID \"" + id + "\" !", e);
        }
    }
}

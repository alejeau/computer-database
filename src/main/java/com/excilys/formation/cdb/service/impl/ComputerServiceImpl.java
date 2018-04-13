package com.excilys.formation.cdb.service.impl;

import com.excilys.formation.cdb.exceptions.DAOException;
import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.exceptions.ValidationException;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistence.DatabaseField;
import com.excilys.formation.cdb.persistence.dao.ComputerDAO;
import com.excilys.formation.cdb.service.ComputerService;
import com.excilys.formation.cdb.validators.ComputerValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("ComputerService")
@EnableTransactionManagement
public class ComputerServiceImpl implements ComputerService {
    private static final Logger LOG = LoggerFactory.getLogger(ComputerServiceImpl.class);

    private ComputerDAO computerDAO;

    @Autowired
    public ComputerServiceImpl(ComputerDAO computerDAO) {
        this.computerDAO = computerDAO;
    }

    @Override
    public Long getNumberOfComputers() throws ServiceException {
        try {
            return computerDAO.getNumberOfComputers();
        } catch (DAOException e) {
            LOG.error("{}", e);
            throw new ServiceException("Couldn't retrieve the number of computers!", e);
        }
    }

    @Override
    public Long getNumberOfComputersWithName(String name) throws ServiceException {
        try {
            return computerDAO.getNumberOfComputersWithName(name);
        } catch (DAOException e) {
            LOG.error("{}", e);
            throw new ServiceException("Couldn't retrieve the number of computers WITH NAME LIKE \"" + name + "\"!", e);
        }
    }

    @Override
    public Computer getComputer(Long id) throws ServiceException {
        try {
            return computerDAO.getComputer(id);
        } catch (DAOException e) {
            LOG.error("{}", e);
            throw new ServiceException("Couldn't retrieve computer with ID " + id + "!", e);
        }
    }

    @Override
    public List<Computer> getComputer(String name, long index, Long limit) throws ServiceException {
        try {
            return computerDAO.getComputersWithName(name, index, limit);
        } catch (DAOException e) {
            LOG.error("{}", e);
            throw new ServiceException("Couldn't retrieve the computers WITH NAME LIKE \"" + name + "\"!", e);
        }
    }

    @Override
    public List<Computer> getComputerOrderedBy(String name, long index, Long limit, DatabaseField computerField, boolean ascending) throws ServiceException {
        try {
            return computerDAO.getComputersWithNameOrderedBy(name, index, limit, computerField, ascending);
        } catch (DAOException e) {
            LOG.error("{}", e);
            throw new ServiceException("Couldn't retrieve the computers WITH NAME LIKE \"" + name + "\"!", e);
        }
    }


    @Override
    public List<Computer> getComputers(long index, Long limit) throws ServiceException {
        try {
            return computerDAO.getComputerList(index, limit);
        } catch (DAOException e) {
            LOG.error("{}", e);
            throw new ServiceException("Couldn't get list of computers from " + index + " to " + limit + "!", e);
        }
    }

    @Override
    public List<Computer> getComputersOrderedBy(long index, Long limit, DatabaseField computerField, boolean ascending) throws ServiceException {
        try {
            return computerDAO.getComputerListOrderedBy(index, limit, computerField, ascending);
        } catch (DAOException e) {
            LOG.error("{}", e);
            throw new ServiceException("Couldn't get list of computers from " + index + " to " + limit + "!", e);
        }
    }


    @Override
    public Long persistComputer(Computer c) throws ValidationException, ServiceException {
        ComputerValidator.validate(c);
        try {
            return computerDAO.persistComputer(c);
        } catch (DAOException e) {
            LOG.error("{}", e);
            throw new ServiceException("Couldn't store the computer!", e);
        }
    }

    @Override
    public void updateComputer(Computer c) throws ValidationException, ServiceException {
        ComputerValidator.validate(c);
        try {
            computerDAO.updateComputer(c);
        } catch (DAOException e) {
            LOG.error("{}", e);
            throw new ServiceException("Couldn't update the computer!", e);
        }
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void deleteComputer(Long id) throws ServiceException {
        try {
            computerDAO.deleteComputer(id);
        } catch (DAOException e) {
            LOG.error("{}", e);
            throw new ServiceException("Couldn't delete the computer with ID \"" + id + "\" !", e);
        }
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void deleteComputers(List<Long> idList) throws ServiceException {
        try {
            computerDAO.deleteComputers(idList);
        } catch (DAOException e) {
            LOG.error("{}", e);
            throw new ServiceException("Couldn't delete the list of computers!", e);
        }
    }
}

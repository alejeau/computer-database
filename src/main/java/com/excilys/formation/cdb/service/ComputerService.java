package com.excilys.formation.cdb.service;

import com.excilys.formation.cdb.exceptions.ValidationException;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.paginator.ComputerPage;
import com.excilys.formation.cdb.paginator.ComputerSearchPage;
import com.excilys.formation.cdb.paginator.core.LimitValue;
import com.excilys.formation.cdb.persistence.dao.impl.ComputerDAOImpl;
import com.excilys.formation.cdb.validators.ComputerValidator;

import java.util.List;

public enum ComputerService {
    INSTANCE;

    ComputerService() {

    }

    public Long getNumberOfComputers() {
        return ComputerDAOImpl.INSTANCE.getNumberOfComputers();
    }

    public Long getNumberOfComputersWithName(String name) {
        return ComputerDAOImpl.INSTANCE.getNumberOfComputersWithName(name);
    }

    public Computer getComputer(Long id) {
        return ComputerDAOImpl.INSTANCE.getComputer(id);
    }

    public List<Computer> getComputer(String name, long index, Long limit) {
        return ComputerDAOImpl.INSTANCE.getComputer(name, index, limit);
    }

    public ComputerSearchPage getComputer(String name, LimitValue limit) {
        return new ComputerSearchPage(name, limit);
    }

    public ComputerPage getComputers(LimitValue limit) {
        return new ComputerPage(limit);
    }

    public List<Computer> getComputers(long index, Long limit) {
        return ComputerDAOImpl.INSTANCE.getComputers(index, limit);
    }

    public Long persistComputer(Computer c) throws ValidationException {
        ComputerValidator.INSTANCE.validate(c);
        return ComputerDAOImpl.INSTANCE.persistComputer(c);
    }

    public void updateComputer(Computer c) throws ValidationException {
        ComputerValidator.INSTANCE.validate(c);
        ComputerDAOImpl.INSTANCE.updateComputer(c);
    }

    public void deleteComputer(Long id) {
        ComputerDAOImpl.INSTANCE.deleteComputer(id);
    }
}

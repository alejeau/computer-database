package com.excilys.formation.cdb.service;

import com.excilys.formation.cdb.exceptions.ValidationException;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.paginator.ComputerPage;
import com.excilys.formation.cdb.paginator.ComputerSearchPage;
import com.excilys.formation.cdb.paginator.core.OFFSET_VALUE;
import com.excilys.formation.cdb.persistence.dao.impl.ComputerDAOImpl;
import com.excilys.formation.cdb.validators.ComputerValidator;

import java.util.List;

public enum ComputerService {
    INSTANCE;

    private ComputerService() {

    }

    public Long getNumberOfComputers() {
        return ComputerDAOImpl.INSTANCE.getNumberOfComputers();
    }

    public Long getNumberOfCompaniesWithName(String name) {
        return ComputerDAOImpl.INSTANCE.getNumberOfComputersWithName(name);
    }

    public Computer getComputer(Long id) {
        return ComputerDAOImpl.INSTANCE.getComputer(id);
    }

    public List<Computer> getComputer(String name, int index, int offset) {
        return ComputerDAOImpl.INSTANCE.getComputer(name, index, offset);
    }

    public ComputerSearchPage getComputer(String name, OFFSET_VALUE offset) {
        return new ComputerSearchPage(name, offset);
    }

    public ComputerPage getComputers(OFFSET_VALUE offset) {
        return new ComputerPage(offset);
    }

    public List<Computer> getComputers(int index, int offset) {
        return ComputerDAOImpl.INSTANCE.getComputers(index, offset);
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

package com.excilys.formation.cdb.service;

import com.excilys.formation.cdb.exceptions.DateException;
import com.excilys.formation.cdb.exceptions.ValidationException;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistance.dao.CompanyDAO;
import com.excilys.formation.cdb.persistance.dao.impl.ComputerDAOImpl;
import com.excilys.formation.cdb.validators.ComputerValidator;

import java.util.List;

public enum ComputerService {
    INSTANCE;

//    ComputerDAOImpl

    private ComputerService() {

    }


    public Long getNumberOfComputers() {
        return ComputerDAOImpl.INSTANCE.getNumberOfComputers();
    }

    public Computer getComputer(Long id) {
        return ComputerDAOImpl.INSTANCE.getComputer(id);
    }

    public List<Computer> getComputer(String name, int index, int offset) {
        return ComputerDAOImpl.INSTANCE.getComputer(name, index, offset);
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

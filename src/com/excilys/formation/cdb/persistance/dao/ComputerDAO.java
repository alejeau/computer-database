package com.excilys.formation.cdb.persistance.dao;

import com.excilys.formation.cdb.model.Computer;

import java.util.List;

public interface ComputerDAO {

    public abstract Long getNumberOfComputers();

    public abstract Computer getComputer(Long id);
    public abstract List<Computer> getComputer(String name, int index, int offset);
    public abstract List<Computer> getComputers(int index, int offset);

    public abstract void updateComputer(Computer c);
    public abstract Long persistComputer(Computer c);
    public abstract void deleteComputer(Long id);

}

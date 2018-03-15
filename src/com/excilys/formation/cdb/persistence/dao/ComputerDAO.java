package com.excilys.formation.cdb.persistence.dao;

import com.excilys.formation.cdb.model.Computer;

import java.util.List;

public interface ComputerDAO {

    public abstract Long getNumberOfComputers();
    public abstract Long getNumberOfComputersWithName(String name);

    public abstract Computer getComputer(Long id);
    public abstract List<Computer> getComputer(String name, int index, int limit);
    public abstract List<Computer> getComputers(int index, int limit);

    public abstract void updateComputer(Computer c);
    public abstract Long persistComputer(Computer c);
    public abstract void deleteComputer(Long id);

}

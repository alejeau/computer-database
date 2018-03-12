package com.excilys.formation.cdb.persistance.dao;

import com.excilys.formation.cdb.model.Computer;

import java.util.List;

public interface ComputerDAO {

    public abstract int getNumberOfComputers();

    public abstract Computer getComputer(int id);
    public abstract List<Computer> getComputer(String name, int index, int offset);
    public abstract List<Computer> getComputers(int index, int stop);

}

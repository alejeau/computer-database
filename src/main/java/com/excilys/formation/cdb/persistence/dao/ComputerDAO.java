package com.excilys.formation.cdb.persistence.dao;

import com.excilys.formation.cdb.model.Computer;

import java.util.List;

public interface ComputerDAO {

    Long getNumberOfComputers();

    Long getNumberOfComputersWithName(String name);

    Computer getComputer(Long id);

    List<Computer> getComputer(String name, long index, Long limit);

    List<Computer> getComputers(long index, Long limit);

    void updateComputer(Computer c);

    Long persistComputer(Computer c);

    void deleteComputer(Long id);
}

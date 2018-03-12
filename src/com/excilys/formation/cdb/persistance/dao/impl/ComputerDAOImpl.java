package com.excilys.formation.cdb.persistance.dao.impl;

import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistance.ConnectionManager;
import com.excilys.formation.cdb.persistance.dao.ComputerDAO;

import java.util.List;

public class ComputerDAOImpl implements ComputerDAO {

    private ConnectionManager connectionManager = ConnectionManager.INSTANCE;

    private static final String NUMBER_OF_COMPANIES = "SELECT COUNT(id) FROM company";
    private static final String COMPANY_BY_ID = "SELECT * FROM company WHERE id=?";
    private static final String COMPANY_BY_NAME = "SELECT * FROM company WHERE name LIKE ? ORDER BY name LIMIT ?, ?";
    private static final String ALL_COMPANIES = "SELECT * FROM company ORDER BY name LIMIT ?, ?";
    
    public int getNumberOfComputers() {
        return -1;
    }

    public Computer getComputer(int id) {
        return null;
    }

    public List<Computer> getComputer(String name, int index, int offset) {
        return null;
    }

    public List<Computer> getComputers(int index, int stop) {
        return null;
    }
    
}

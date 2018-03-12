package com.excilys.formation.cdb.persistance.dao.impl;

import com.excilys.formation.cdb.mapper.CompanyMapper;
import com.excilys.formation.cdb.mapper.ComputerMapper;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistance.ConnectionManager;
import com.excilys.formation.cdb.persistance.dao.ComputerDAO;

import java.sql.*;
import java.util.List;

public class ComputerDAOImpl implements ComputerDAO {

    private ConnectionManager connectionManager = ConnectionManager.INSTANCE;

    private static final String NUMBER_OF_COMPUTERS = "SELECT COUNT(id) FROM computer";
    private static final String SELECT_COMPUTER_BY_ID = "SELECT * FROM computer WHERE id=?";
    private static final String SELECT_COMPUTER_BY_NAME = "SELECT * FROM computer WHERE name LIKE ? ORDER BY name LIMIT ?, ?";
    private static final String SELECT_ALL_COMPUTERS = "SELECT * FROM computer ORDER BY name LIMIT ?, ?";
    
    public int getNumberOfComputers() {
        SimpleDAOImpl simpleDao = new SimpleDAOImpl();
        return simpleDao.select(NUMBER_OF_COMPUTERS);
    }


    public Computer getComputer(int id) {
        Connection conn = connectionManager.getConnection();
        PreparedStatement prep_stmt = null;
        ResultSet rs = null;
        Computer c = null;

        try {
            prep_stmt = conn.prepareStatement(SELECT_COMPUTER_BY_ID);
            prep_stmt.setInt(1, id);
            rs = prep_stmt.executeQuery();
            c = ComputerMapper.mapElement(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.closeElements(conn, prep_stmt, rs);
        }

        return c;
    }

    public List<Computer> getComputer(String name, int index, int offset) {
        return null;
    }

    public List<Computer> getComputers(int index, int stop) {
        return null;
    }

    public static void main(String[] args) {
        ComputerDAOImpl cdi = new ComputerDAOImpl();
        Computer c = cdi.getComputer(1);
        System.out.println(c);
    }
}

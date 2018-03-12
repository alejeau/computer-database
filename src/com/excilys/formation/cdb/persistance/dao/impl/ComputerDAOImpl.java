package com.excilys.formation.cdb.persistance.dao.impl;

import com.excilys.formation.cdb.mapper.ComputerMapper;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistance.ConnectionManager;
import com.excilys.formation.cdb.persistance.dao.ComputerDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComputerDAOImpl implements ComputerDAO {

    private ConnectionManager connectionManager = ConnectionManager.INSTANCE;

    private static final String NUMBER_OF_COMPUTERS = "SELECT COUNT(id) FROM computer;";
    private static final String SELECT_COMPUTER_BY_ID = "SELECT * FROM computer WHERE id=?;";
    private static final String SELECT_COMPUTER_BY_NAME = "SELECT * FROM computer WHERE name LIKE ? ORDER BY name LIMIT ?, ?;";
    private static final String SELECT_ALL_COMPUTERS = "SELECT * FROM computer ORDER BY name LIMIT ?, ?;";
    private static final String INSERT_COMPUTER = "INSERT INTO computer (id, name, introduced, discontinued, company_id) values (?, ?, ?, ?);";
    
    public Integer getNumberOfComputers() {
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
            c = ComputerMapper.map(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.closeElements(conn, prep_stmt, rs);
        }

        return c;
    }

    public List<Computer> getComputer(String name, int index, int offset) {
        Connection conn = connectionManager.getConnection();
        PreparedStatement prep_stmt = null;
        ResultSet rs = null;
        List<Computer> computers = new ArrayList<>();

        try {
            prep_stmt = conn.prepareStatement(SELECT_COMPUTER_BY_NAME);
            prep_stmt.setString(1, "%" + name + "%");
            prep_stmt.setInt(2, index);
            prep_stmt.setInt(3, offset);
            rs = prep_stmt.executeQuery();
            computers = ComputerMapper.mapList(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.closeElements(conn, prep_stmt, rs);
        }

        return computers;
    }

    public List<Computer> getComputers(int index, int offset) {
        Connection conn = connectionManager.getConnection();
        PreparedStatement prep_stmt = null;
        ResultSet rs = null;
        List<Computer> computers = new ArrayList<>();

        try {
            prep_stmt = conn.prepareStatement(SELECT_ALL_COMPUTERS);
            prep_stmt.setInt(1, index);
            prep_stmt.setInt(2, offset);
            rs = prep_stmt.executeQuery();
            computers = ComputerMapper.mapList(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.closeElements(conn, prep_stmt, rs);
        }

        return computers;
    }


    public Integer persistComputer(Computer c) {
        Connection conn = connectionManager.getConnection();
        PreparedStatement prep_stmt = null;
        ResultSet rs = null;
        Integer createdId = null;

        try {
            prep_stmt = conn.prepareStatement(INSERT_COMPUTER, Statement.RETURN_GENERATED_KEYS);
            prep_stmt.setString(1, c.getName());
            prep_stmt.setDate(2, Date.valueOf(c.getIntroduced()));
            prep_stmt.setDate(3, Date.valueOf(c.getDiscontinued()));
            prep_stmt.setInt(4, c.getCompanyId());
            prep_stmt.executeUpdate();

            rs = prep_stmt.getGeneratedKeys();
            if(rs.next()) {
                createdId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.closeElements(conn, prep_stmt, rs);
        }

        return createdId;
    }

    public Integer updateComputer(Computer c) {
        return null;
    }

    public Computer deleteComputer(Computer c) {
        return null;
    }

    public static void main(String[] args) {
        ComputerDAOImpl cdi = new ComputerDAOImpl();
        Computer c = cdi.getComputer(1);
        System.out.println(c);
    }
}

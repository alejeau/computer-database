package com.excilys.formation.cdb.persistence.dao.impl;

import com.excilys.formation.cdb.mapper.ComputerMapper;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistence.ConnectionManager;
import com.excilys.formation.cdb.persistence.dao.ComputerDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public enum ComputerDAOImpl implements ComputerDAO {
    INSTANCE;

    private static ConnectionManager connectionManager = ConnectionManager.INSTANCE;

    private static final String NUMBER_OF_COMPUTERS = "SELECT COUNT(computer_id) FROM computer;";
    private static final String NUMBER_OF_COMPUTERS_WITH_NAME = "SELECT COUNT(computer_id) FROM computer WHERE computer_name LIKE ?;";
    private static final String SELECT_COMPUTER_BY_ID = "SELECT * FROM computer LEFT JOIN company ON computer_company_id=company_id WHERE computer_id=?;";
    private static final String SELECT_COMPUTER_BY_NAME = "SELECT * FROM computer LEFT JOIN company ON computer_company_id=company_id WHERE computer_name LIKE ? ORDER BY computer_name LIMIT ?, ?;";
    private static final String SELECT_ALL_COMPUTERS = "SELECT * FROM computer LEFT JOIN company ON computer_company_id=company_id ORDER BY computer_name LIMIT ?, ?;";
    private static final String INSERT_COMPUTER = "INSERT INTO computer (computer_name, computer_introduced, computer_discontinued, computer_company_id) values (?, ?, ?, ?);";
    private static final String UPDATE_COMPUTER = "UPDATE computer SET computer_name = ?, computer_introduced = ?, computer_discontinued = ?, computer_company_id = ? WHERE computer_id = ?;";
    private static final String DELETE_COMPUTER = "DELETE from computer WHERE computer_id = ?;";

    private ComputerDAOImpl() {

    }

    public Long getNumberOfComputers() {
//        SimpleDAOImpl simpleDao = SimpleDAOImpl.INSTANCE;
        return SimpleDAOImpl.INSTANCE.count(NUMBER_OF_COMPUTERS);
    }

    public Long getNumberOfComputersWithName(String name) {
        SimpleDAOImpl simpleDao = SimpleDAOImpl.INSTANCE;
        return simpleDao.countElementsWithName(NUMBER_OF_COMPUTERS_WITH_NAME, name);
    }

    public Computer getComputer(Long id) {
        Connection conn = connectionManager.getConnection();
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        Computer c = null;

        try {
            prepStmt = conn.prepareStatement(SELECT_COMPUTER_BY_ID);
            prepStmt.setLong(1, id);
            rs = prepStmt.executeQuery();
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            c = ComputerMapper.map(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.closeElements(conn, prepStmt, rs);
        }

        return c;
    }

    public List<Computer> getComputer(String name, int index, int limit) {
        Connection conn = connectionManager.getConnection();
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        List<Computer> computers = new ArrayList<>();

        try {
            prepStmt = conn.prepareStatement(SELECT_COMPUTER_BY_NAME);
            prepStmt.setString(1, "%" + name + "%");
            prepStmt.setLong(2, index);
            prepStmt.setLong(3, limit);
            rs = prepStmt.executeQuery();
            computers = ComputerMapper.mapList(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.closeElements(conn, prepStmt, rs);
        }

        return computers;
    }

    public List<Computer> getComputers(int index, int limit) {
        Connection conn = connectionManager.getConnection();
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        List<Computer> computers = new ArrayList<>();

        try {
            prepStmt = conn.prepareStatement(SELECT_ALL_COMPUTERS);
            prepStmt.setLong(1, index);
            prepStmt.setLong(2, limit);
            rs = prepStmt.executeQuery();
            computers = ComputerMapper.mapList(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.closeElements(conn, prepStmt, rs);
        }

        return computers;
    }

    public Long persistComputer(Computer c) {
        Connection conn = connectionManager.getConnection();
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        Long createdId = null;

        try {
            prepStmt = conn.prepareStatement(INSERT_COMPUTER, Statement.RETURN_GENERATED_KEYS);
            prepStmt.setString(1, c.getName());
            if (c.getIntroduced() != null)
                prepStmt.setDate(2, Date.valueOf(c.getIntroduced()));
            else
                prepStmt.setNull(2, Types.DATE);
            if (c.getDiscontinued() !=  null)
                prepStmt.setDate(3, Date.valueOf(c.getDiscontinued()));
            else
                prepStmt.setNull(3, Types.DATE);
            prepStmt.setLong(4, c.getCompany().getId());
            prepStmt.executeUpdate();

            rs = prepStmt.getGeneratedKeys();
            if(rs.next()) {
                createdId = rs.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.closeElements(conn, prepStmt, rs);
        }

        return createdId;
    }

    public void updateComputer(Computer c) {
        Connection conn = connectionManager.getConnection();
        PreparedStatement prepStmt = null;

        try {
            prepStmt = conn.prepareStatement(UPDATE_COMPUTER);
            prepStmt.setString(1, c.getName());
            if (c.getIntroduced() != null)
                prepStmt.setDate(2, Date.valueOf(c.getIntroduced()));
            else
                prepStmt.setNull(2, Types.DATE);
            if (c.getDiscontinued() !=  null)
                prepStmt.setDate(3, Date.valueOf(c.getDiscontinued()));
            else
                prepStmt.setNull(3, Types.DATE);
            prepStmt.setLong(4, c.getCompany().getId());
            prepStmt.setLong(5, c.getId());
            prepStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.closeElements(conn, prepStmt, null);
        }
    }

    public void deleteComputer(Long id) {
        Connection conn = connectionManager.getConnection();
        PreparedStatement prepStmt = null;

        try {
            prepStmt = conn.prepareStatement(DELETE_COMPUTER);
            prepStmt.setLong(1, id);
            prepStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.closeElements(conn, prepStmt, null);
        }
    }
}

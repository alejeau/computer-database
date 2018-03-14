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

    private static final String NUMBER_OF_COMPUTERS = "SELECT COUNT(id) FROM computer;";
    private static final String SELECT_COMPUTER_BY_ID = "SELECT * FROM computer LEFT JOIN company ON computer_company_id=company_id WHERE computer_id=?;";
    private static final String SELECT_COMPUTER_BY_NAME = "SELECT * FROM computer WHERE name LIKE ? ORDER BY name LIMIT ?, ?;";
    private static final String SELECT_ALL_COMPUTERS = "SELECT * FROM computer ORDER BY name LIMIT ?, ?;";
    private static final String INSERT_COMPUTER = "INSERT INTO computer (computer_name, computer_introduced, computer_discontinued, computer_company_id) values (?, ?, ?, ?);";
    private static final String UPDATE_COMPUTER = "UPDATE computer SET computer_name = ?, computer_introduced = ?, computer_discontinued = ?, computer_company_id = ? WHERE id = ?;";
    private static final String DELETE_COMPUTER = "DELETE from computer WHERE computer_id = ?;";

    private ComputerDAOImpl() {

    }

    public Long getNumberOfComputers() {
        SimpleDAOImpl simpleDao = SimpleDAOImpl.INSTANCE;
        return simpleDao.select(NUMBER_OF_COMPUTERS);
    }

    public Computer getComputer(Long id) {
        Connection conn = connectionManager.getConnection();
        PreparedStatement prep_stmt = null;
        ResultSet rs = null;
        Computer c = null;

        try {
            prep_stmt = conn.prepareStatement(SELECT_COMPUTER_BY_ID);
            prep_stmt.setLong(1, id);
            rs = prep_stmt.executeQuery();
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            int count = resultSetMetaData.getColumnCount();
            for (int i = 1; i <= count; i++)
                System.out.println(resultSetMetaData.getColumnName(i) + ": " + resultSetMetaData.getColumnLabel(i));
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
            prep_stmt.setLong(2, index);
            prep_stmt.setLong(3, offset);
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
            prep_stmt.setLong(1, index);
            prep_stmt.setLong(2, offset);
            rs = prep_stmt.executeQuery();
            computers = ComputerMapper.mapList(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.closeElements(conn, prep_stmt, rs);
        }

        return computers;
    }

    public Long persistComputer(Computer c) {
        Connection conn = connectionManager.getConnection();
        PreparedStatement prep_stmt = null;
        ResultSet rs = null;
        Long createdId = null;

        try {
            prep_stmt = conn.prepareStatement(INSERT_COMPUTER, Statement.RETURN_GENERATED_KEYS);
            prep_stmt.setString(1, c.getName());
            if (c.getIntroduced() != null)
                prep_stmt.setDate(2, Date.valueOf(c.getIntroduced()));
            else
                prep_stmt.setNull(2, Types.DATE);
            if (c.getDiscontinued() !=  null)
                prep_stmt.setDate(3, Date.valueOf(c.getDiscontinued()));
            else
                prep_stmt.setNull(3, Types.DATE);
            prep_stmt.setLong(4, c.getCompany().getId());
            prep_stmt.executeUpdate();

            rs = prep_stmt.getGeneratedKeys();
            if(rs.next()) {
                createdId = rs.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.closeElements(conn, prep_stmt, rs);
        }

        return createdId;
    }

    public void updateComputer(Computer c) {
        Connection conn = connectionManager.getConnection();
        PreparedStatement prep_stmt = null;

        try {
            prep_stmt = conn.prepareStatement(UPDATE_COMPUTER);
            prep_stmt.setString(1, c.getName());
            if (c.getIntroduced() != null)
                prep_stmt.setDate(2, Date.valueOf(c.getIntroduced()));
            else
                prep_stmt.setNull(2, Types.DATE);
            if (c.getDiscontinued() !=  null)
                prep_stmt.setDate(3, Date.valueOf(c.getDiscontinued()));
            else
                prep_stmt.setNull(3, Types.DATE);
            prep_stmt.setLong(4, c.getCompany().getId());
            prep_stmt.setLong(5, c.getId());
            prep_stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.closeElements(conn, prep_stmt, null);
        }
    }

    public void deleteComputer(Long id) {
        Connection conn = connectionManager.getConnection();
        PreparedStatement prep_stmt = null;

        try {
            prep_stmt = conn.prepareStatement(DELETE_COMPUTER);
            prep_stmt.setLong(1, id);
            prep_stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.closeElements(conn, prep_stmt, null);
        }
    }
}

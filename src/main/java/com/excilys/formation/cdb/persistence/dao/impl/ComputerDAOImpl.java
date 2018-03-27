package com.excilys.formation.cdb.persistence.dao.impl;

import com.excilys.formation.cdb.exceptions.DAOException;
import com.excilys.formation.cdb.mapper.model.ComputerMapper;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistence.dao.ComputerDAO;
import com.excilys.formation.cdb.persistence.impl.ConnectionManagerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;

import static com.excilys.formation.cdb.persistence.dao.impl.DbFields.COMPUTER_AND_COMPANY_STAR;

public enum ComputerDAOImpl implements ComputerDAO {
    INSTANCE;

    private static final Logger LOG = LoggerFactory.getLogger(ComputerDAOImpl.class);
    private static ConnectionManagerImpl connectionManager = ConnectionManagerImpl.INSTANCE;

    private static final String NUMBER_OF_COMPUTERS = "SELECT COUNT(computer_id) FROM computer;";
    private static final String NUMBER_OF_COMPUTERS_WITH_NAME = "SELECT COUNT(computer_id) FROM computer WHERE computer_name LIKE ?;";
    private static final String SELECT_COMPUTER_BY_ID = "SELECT " + COMPUTER_AND_COMPANY_STAR + " FROM computer LEFT JOIN company ON computer_company_id=company.company_id WHERE computer_id=?;";
    private static final String SELECT_COMPUTER_BY_NAME = "SELECT " + COMPUTER_AND_COMPANY_STAR + " FROM computer LEFT JOIN company ON computer_company_id=company.company_id WHERE computer_name LIKE ? ORDER BY computer_name LIMIT ?, ?;";
    private static final String SELECT_ALL_COMPUTERS = "SELECT " + COMPUTER_AND_COMPANY_STAR + " FROM computer LEFT JOIN company ON computer_company_id=company.company_id ORDER BY computer_name LIMIT ?, ?;";
    private static final String INSERT_COMPUTER = "INSERT INTO computer (computer_name, computer_introduced, computer_discontinued, computer_company_id) values (?, ?, ?, ?);";
    private static final String UPDATE_COMPUTER = "UPDATE computer SET computer_name = ?, computer_introduced = ?, computer_discontinued = ?, computer_company_id = ? WHERE computer_id = ?;";
    private static final String DELETE_COMPUTER = "DELETE from computer WHERE computer_id = ?;";

    ComputerDAOImpl() {

    }

    public Long getNumberOfComputers() throws DAOException {
        LOG.debug("getNumberOfComputers");
        return SimpleDAOImpl.INSTANCE.count(NUMBER_OF_COMPUTERS);
    }

    public Long getNumberOfComputersWithName(String name) throws DAOException {
        LOG.debug("getNumberOfComputersWithName");
        SimpleDAOImpl simpleDao = SimpleDAOImpl.INSTANCE;
        return simpleDao.countElementsWithName(NUMBER_OF_COMPUTERS_WITH_NAME, name);
    }

    public Computer getComputer(Long id) throws DAOException {
        LOG.debug("getComputer");
        Connection conn = connectionManager.getConnection();
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        Computer c;

        try {
            prepStmt = conn.prepareStatement(SELECT_COMPUTER_BY_ID);
            prepStmt.setLong(1, id);

            LOG.debug("Executing query \"" + prepStmt + "\"");
            rs = prepStmt.executeQuery();
            c = ComputerMapper.map(rs);
        } catch (SQLException e) {
            LOG.error("{}", e);
            throw new DAOException("Couldn't get computer with ID " + id + "!", e);
        } finally {
            ConnectionManagerImpl.closeElements(conn, prepStmt, rs);
        }

        LOG.debug("Returning " + c);
        return c;
    }

    public List<Computer> getComputer(String name, long index, Long limit) throws DAOException {
        LOG.debug("getComputer");
        Connection conn = connectionManager.getConnection();
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        List<Computer> computers;

        try {
            prepStmt = conn.prepareStatement(SELECT_COMPUTER_BY_NAME);
            prepStmt.setString(1, "%" + name + "%");
            prepStmt.setLong(2, index);
            prepStmt.setLong(3, limit);

            LOG.debug("Executing query \"" + prepStmt + "\"");
            rs = prepStmt.executeQuery();
            computers = ComputerMapper.mapList(rs);
        } catch (SQLException e) {
            LOG.error("{}", e);
            throw new DAOException("Couldn't get list of computers with NAME LIKE " + name + "!", e);
        } finally {
            ConnectionManagerImpl.closeElements(conn, prepStmt, rs);
        }

        LOG.debug("Returning list of size " + computers.size());
        return computers;
    }

    public List<Computer> getComputers(long index, Long limit) throws DAOException {
        LOG.debug("getComputers");
        Connection conn = connectionManager.getConnection();
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        List<Computer> computers;

        try {
            prepStmt = conn.prepareStatement(SELECT_ALL_COMPUTERS);
            prepStmt.setLong(1, index);
            prepStmt.setLong(2, limit);

            LOG.debug("Executing query \"" + prepStmt + "\"");
            rs = prepStmt.executeQuery();

            computers = ComputerMapper.mapList(rs);
        } catch (SQLException e) {
            LOG.error("{}", e);
            throw new DAOException("Couldn't get list of computers from " + index + " to " + limit + "!", e);
        } finally {
            ConnectionManagerImpl.closeElements(conn, prepStmt, rs);
        }

        LOG.debug("Returning list of size " + computers.size());
        return computers;
    }

    public Long persistComputer(Computer computer) throws DAOException {
        LOG.debug("persistComputer");
        Connection conn = connectionManager.getConnection();
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        Long createdId = null;

        try {
            prepStmt = conn.prepareStatement(INSERT_COMPUTER, Statement.RETURN_GENERATED_KEYS);
            prepStmt.setString(1, computer.getName());
            if (computer.getIntroduced() != null) {
                prepStmt.setDate(2, Date.valueOf(computer.getIntroduced()));
            } else {
                prepStmt.setNull(2, Types.DATE);
            }

            if (computer.getDiscontinued() != null) {
                prepStmt.setDate(3, Date.valueOf(computer.getDiscontinued()));
            } else {
                prepStmt.setNull(3, Types.DATE);
            }
            if (computer.getCompany() != null && computer.getCompany().getId() != null) {
                prepStmt.setLong(4, computer.getCompany().getId());
            } else {
                prepStmt.setNull(4, Types.BIGINT);
            }
            prepStmt.executeUpdate();

            LOG.debug("Executing query \"" + prepStmt + "\"");
            rs = prepStmt.getGeneratedKeys();
            if (rs.next()) {
                createdId = rs.getLong(1);
            }
        } catch (SQLException e) {
            LOG.error("{}", e);
            throw new DAOException("Couldn't persist the computer " + computer.shortToString() + ".", e);
        } finally {
            ConnectionManagerImpl.closeElements(conn, prepStmt, rs);
        }

        LOG.debug("Returning id " + createdId);
        return createdId;
    }

    public void updateComputer(Computer computer) throws DAOException {
        LOG.debug("updateComputer");
        Connection conn = connectionManager.getConnection();
        PreparedStatement prepStmt = null;

        try {
            prepStmt = conn.prepareStatement(UPDATE_COMPUTER);
            prepStmt.setString(1, computer.getName());
            if (computer.getIntroduced() != null) {
                prepStmt.setDate(2, Date.valueOf(computer.getIntroduced()));
            } else {
                prepStmt.setNull(2, Types.DATE);
            }
            if (computer.getDiscontinued() != null) {
                prepStmt.setDate(3, Date.valueOf(computer.getDiscontinued()));
            } else {
                prepStmt.setNull(3, Types.DATE);
            }
            prepStmt.setLong(4, computer.getCompany().getId());
            prepStmt.setLong(5, computer.getId());

            LOG.debug("Executing query \"" + prepStmt + "\"");
            prepStmt.executeUpdate();
        } catch (SQLException e) {
            LOG.error("{}", e);
            throw new DAOException("Couldn't update the computer with ID " + computer.getId() + ".", e);
        } finally {
            ConnectionManagerImpl.closeElements(conn, prepStmt, null);
        }
    }

    public void deleteComputer(Long id) throws DAOException {
        LOG.debug("deleteComputer");
        Connection conn = connectionManager.getConnection();
        PreparedStatement prepStmt = null;

        try {
            prepStmt = conn.prepareStatement(DELETE_COMPUTER);
            prepStmt.setLong(1, id);

            LOG.debug("Executing query \"" + prepStmt + "\"");
            prepStmt.executeUpdate();
        } catch (SQLException e) {
            LOG.error("{}", e);
            throw new DAOException("Couldn't delete the computer with ID " + id + ".", e);
        } finally {
            ConnectionManagerImpl.closeElements(conn, prepStmt, null);
        }
    }
}

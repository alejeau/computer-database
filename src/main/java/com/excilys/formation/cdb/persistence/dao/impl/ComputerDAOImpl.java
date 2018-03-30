package com.excilys.formation.cdb.persistence.dao.impl;

import com.excilys.formation.cdb.exceptions.ConnectionException;
import com.excilys.formation.cdb.exceptions.DAOException;
import com.excilys.formation.cdb.exceptions.MapperException;
import com.excilys.formation.cdb.mapper.model.ComputerMapper;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistence.ConnectionManager;
import com.excilys.formation.cdb.persistence.DatabaseField;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.excilys.formation.cdb.persistence.dao.impl.DbFields.COMPUTER_AND_COMPANY_STAR;

public enum ComputerDAOImpl implements ComputerDAO {
    INSTANCE;

    private static final Logger LOG = LoggerFactory.getLogger(ComputerDAOImpl.class);
    private static ConnectionManager connectionManager = ConnectionManagerImpl.INSTANCE;

    private static final String ORDER_FIELD = "{orderField}";
    private static final String DIRECTION = "{direction}";
    private static final String ASCENDING = "ASC";
    private static final String DESCENDING = "DESC";

    private static final String NUMBER_OF_COMPUTERS = "SELECT COUNT(computer_id) FROM computer;";
    private static final String NUMBER_OF_COMPUTERS_WITH_NAME_OR_COMPANY_NAME = "SELECT COUNT(computer_id) FROM computer LEFT JOIN company ON computer_company_id=company.company_id WHERE computer_name LIKE ? OR company.company_name LIKE ?;";
    private static final String SELECT_COMPUTER_BY_ID = "SELECT " + COMPUTER_AND_COMPANY_STAR + " FROM computer LEFT JOIN company ON computer_company_id=company.company_id WHERE computer_id=?;";
    private static final String SELECT_COMPUTER_BY_NAME_OR_COMPANY_NAME = "SELECT " + COMPUTER_AND_COMPANY_STAR + " FROM computer LEFT JOIN company ON computer_company_id=company.company_id WHERE computer_name LIKE ? OR company.company_name LIKE ? ORDER BY computer_name LIMIT ?, ?;";
    private static final String SELECT_COMPUTER_BY_NAME_OR_COMPANY_NAME_ORDERED_BY = "SELECT " + COMPUTER_AND_COMPANY_STAR + " FROM computer LEFT JOIN company ON computer_company_id=company.company_id WHERE computer_name LIKE ? OR company.company_name LIKE ? ORDER BY " + ORDER_FIELD + " " + DIRECTION + " LIMIT ?, ?;";
    private static final String SELECT_ALL_COMPUTERS = "SELECT " + COMPUTER_AND_COMPANY_STAR + " FROM computer LEFT JOIN company ON computer_company_id=company.company_id ORDER BY computer_name LIMIT ?, ?;";
    private static final String SELECT_ALL_COMPUTERS_ORDERED_BY = "SELECT " + COMPUTER_AND_COMPANY_STAR + " FROM computer LEFT JOIN company ON computer_company_id=company.company_id ORDER BY " + ORDER_FIELD + " " + DIRECTION + " LIMIT ?, ?;";
    private static final String INSERT_COMPUTER = "INSERT INTO computer (computer_name, computer_introduced, computer_discontinued, computer_company_id) values (?, ?, ?, ?);";
    private static final String UPDATE_COMPUTER = "UPDATE computer SET computer_name = ?, computer_introduced = ?, computer_discontinued = ?, computer_company_id = ? WHERE computer_id = ?;";
    private static final String DELETE_COMPUTER = "DELETE from computer WHERE computer_id = ?;";

    ComputerDAOImpl() {

    }

    @Override
    public Long getNumberOfComputers() throws DAOException {
        LOG.debug("getNumberOfComputers");
        return SimpleDAOImpl.INSTANCE.count(NUMBER_OF_COMPUTERS);
    }

    @Override
    public Long getNumberOfComputersWithName(String name) throws DAOException {
        LOG.debug("getNumberOfComputersWithName");
        SimpleDAOImpl simpleDao = SimpleDAOImpl.INSTANCE;
        return simpleDao.countWithStringParameters(NUMBER_OF_COMPUTERS_WITH_NAME_OR_COMPANY_NAME, Arrays.asList(name, name));
    }

    @Override
    public Computer getComputer(Long id) throws DAOException {
        LOG.debug("getComputer");
        Connection conn = this.getConnection();
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        Computer c;

        try {
            prepStmt = conn.prepareStatement(SELECT_COMPUTER_BY_ID);
            prepStmt.setLong(1, id);

            LOG.debug("Executing query \"" + prepStmt + "\"");
            rs = prepStmt.executeQuery();
            try {
                c = ComputerMapper.map(rs);
            } catch (MapperException e) {
                LOG.error("{}", e);
                throw new DAOException("Error while mapping the ResultSet!", e);
            }
        } catch (SQLException e) {
            LOG.error("{}", e);
            throw new DAOException("Couldn't get computer with ID " + id + "!", e);
        } finally {
            ConnectionManagerImpl.closeElements(conn, prepStmt, rs);
        }

        LOG.debug("Returning " + c);
        return c;
    }

    @Override
    public List<Computer> getComputer(String name, long index, Long limit) throws DAOException {
        LOG.debug("getComputer");
        return getComputerOrderedBy(name, index, limit, DatabaseField.COMPUTER_NAME, true);
    }

    @Override
    public List<Computer> getComputerOrderedBy(String name, long index, Long limit, DatabaseField computerField, boolean ascending) throws DAOException {
        LOG.debug("getComputerOrderedBy");
        Connection conn = this.getConnection();
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        List<Computer> computers;
        final String QUERY = SELECT_COMPUTER_BY_NAME_OR_COMPANY_NAME_ORDERED_BY
                .replace(ORDER_FIELD, computerField.getValue())
                .replace(DIRECTION, ascending ? ASCENDING : DESCENDING);
        try {
            prepStmt = conn.prepareStatement(QUERY);
            prepStmt.setString(1, "%" + name + "%");
            prepStmt.setString(2, "%" + name + "%");
            prepStmt.setLong(3, index);
            prepStmt.setLong(4, limit);

            LOG.debug("Executing query \"" + prepStmt + "\"");
            rs = prepStmt.executeQuery();
            try {
                computers = ComputerMapper.mapList(rs);
            } catch (MapperException e) {
                LOG.error("{}", e);
                throw new DAOException("Error while mapping the ResultSet!", e);
            }
        } catch (SQLException e) {
            LOG.error("{}", e);
            throw new DAOException("Couldn't get list of computers with NAME LIKE " + name + "!", e);
        } finally {
            ConnectionManagerImpl.closeElements(conn, prepStmt, rs);
        }

        LOG.debug("Returning list of size " + computers.size());
        return computers;
    }

    @Override
    public List<Computer> getComputers(long index, Long limit) throws DAOException {
        return getComputersOrderedBy(index, limit, DatabaseField.COMPUTER_NAME, true);
    }

    @Override
    public List<Computer> getComputersOrderedBy(long index, Long limit, DatabaseField computerField, boolean ascending) throws DAOException {
        LOG.debug("getComputers");
        Connection conn = this.getConnection();
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        List<Computer> computers;
        final String QUERY = SELECT_ALL_COMPUTERS_ORDERED_BY
                .replace(ORDER_FIELD, computerField.getValue())
                .replace(DIRECTION, ascending ? ASCENDING : DESCENDING);

        try {
            prepStmt = conn.prepareStatement(QUERY);
            prepStmt.setLong(1, index);
            prepStmt.setLong(2, limit);

            LOG.debug("Executing query \"" + prepStmt + "\"");
            rs = prepStmt.executeQuery();

            try {
                computers = ComputerMapper.mapList(rs);
            } catch (MapperException e) {
                LOG.error("{}", e);
                throw new DAOException("Error while mapping the ResultSet!", e);
            }
        } catch (SQLException e) {
            LOG.error("{}", e);
            throw new DAOException("Couldn't get list of computers from " + index + " to " + limit + "!", e);
        } finally {
            ConnectionManagerImpl.closeElements(conn, prepStmt, rs);
        }

        LOG.debug("Returning list of size " + computers.size());
        return computers;
    }


    @Override
    public Long persistComputer(Computer computer) throws DAOException {
        LOG.debug("persistComputer");
        Connection conn = this.getConnection();
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

    @Override
    public void updateComputer(Computer computer) throws DAOException {
        LOG.debug("updateComputer");
        LOG.debug("Computer: {}", computer);
        Connection conn = this.getConnection();
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
            if (computer.getCompany() != null && computer.getCompany().getId() != null) {
                prepStmt.setLong(4, computer.getCompany().getId());
            } else {
                prepStmt.setNull(4, Types.BIGINT);
            }
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

    @Override
    public void deleteComputer(Long id) throws DAOException {
        LOG.debug("deleteComputer");
        this.deleteComputers(Collections.singletonList(id));
    }

    @Override
    public void deleteComputers(List<Long> idList) throws DAOException {
        LOG.debug("deleteComputers (by list)");
        Connection connection = this.getConnection();

        try {
            connection.setAutoCommit(false);
            deleteComputers(idList, connection);
            connection.commit();
        } catch (SQLException e1) {
            try {
                connection.rollback();
            } catch (SQLException e2) {
                LOG.error("{}", e2);
                throw new DAOException("An error occurred while rolling back the changes!");
            }

            LOG.error("{}", e1);
            throw new DAOException("Couldn't delete the supplied list of computers.", e1);
        } finally {
            ConnectionManagerImpl.closeElements(connection, null, null);
        }
    }

    @Override
    public void deleteComputers(List<Long> idList, Connection connection) throws DAOException {
        LOG.debug("deleteComputers (by list) with connection");

        try (PreparedStatement prepStmt = connection.prepareStatement(DELETE_COMPUTER)) {
            for (Long id : idList) {
                prepStmt.setLong(1, id);
                LOG.debug("Executing query \"" + prepStmt + "\"");
                prepStmt.executeUpdate();
            }
        } catch (SQLException e) {
            LOG.error("{}", e);
            throw new DAOException("Couldn't delete the supplied list of computers.", e);
        }
    }


    private Connection getConnection() throws DAOException {
        Connection conn;
        try {
            conn = connectionManager.getConnection();
        } catch (ConnectionException e) {
            LOG.error("{}", e);
            throw new DAOException("Couldn't obtain a connection!", e);
        }
        return conn;
    }
}

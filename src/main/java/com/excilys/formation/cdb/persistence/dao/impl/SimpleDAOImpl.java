package com.excilys.formation.cdb.persistence.dao.impl;

import com.excilys.formation.cdb.exceptions.ConnectionException;
import com.excilys.formation.cdb.exceptions.DAOException;
import com.excilys.formation.cdb.persistence.ConnectionManager;
import com.excilys.formation.cdb.persistence.dao.SimpleDAO;
import com.excilys.formation.cdb.persistence.impl.HikariCPImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository("SimpleDAO")
public class SimpleDAOImpl implements SimpleDAO {
    private static final Logger LOG = LoggerFactory.getLogger(SimpleDAOImpl.class);
    private ConnectionManager connectionManager = HikariCPImpl.INSTANCE;

    SimpleDAOImpl() {
    }

    @Override
    public Long count(String query) throws DAOException {
        LOG.debug("count:");
        Connection conn = this.getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        Long l = null;

        try {
            stmt = conn.createStatement();

            LOG.debug("Executing query \"{}\"", query);
            rs = stmt.executeQuery(query);
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    l = rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            LOG.error("{}", e);
            throw new DAOException("Couldn't execute the requested COUNT query!", e);
        } finally {
            connectionManager.closeElements(conn, stmt, rs);
        }

        LOG.debug("Returning {}", l);
        return l;
    }

    @Override
    public Long countElementsWithName(String query, String name) throws DAOException {
        LOG.debug("countElementsWithName");
        Connection conn = this.getConnection();
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        Long numberOfElements = null;

        try {
            prepStmt = conn.prepareStatement(query);
            prepStmt.setString(1, "%" + name + "%");

            LOG.debug("Executing query \"{}\"", prepStmt);
            rs = prepStmt.executeQuery();
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    numberOfElements = rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            LOG.error("{}", e);
            throw new DAOException("Couldn't execute the requested COUNT LIKE query!", e);
        } finally {
            connectionManager.closeElements(conn, prepStmt, rs);
        }

        LOG.debug("Returning {}", numberOfElements);
        return numberOfElements;
    }

    public Long countWithStringParameters(String query, List<String> params) throws DAOException {
        LOG.debug("countWithStringParameters");
        Connection conn = this.getConnection();
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        Long numberOfElements = null;

        try {
            prepStmt = conn.prepareStatement(query);
            for (int i = 0; i < params.size(); i++) {
                prepStmt.setString(i + 1, "%" + params.get(i) + "%");
            }

            LOG.debug("Executing query \"{}\"", prepStmt);
            rs = prepStmt.executeQuery();
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    numberOfElements = rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            LOG.error("{}", e);
            throw new DAOException("Couldn't execute the requested COUNT LIKE query!", e);
        } finally {
            connectionManager.closeElements(conn, prepStmt, rs);
        }

        LOG.debug("Returning {}", numberOfElements);
        return numberOfElements;
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

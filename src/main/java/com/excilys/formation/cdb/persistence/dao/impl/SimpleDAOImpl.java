package com.excilys.formation.cdb.persistence.dao.impl;

import com.excilys.formation.cdb.exceptions.DAOException;
import com.excilys.formation.cdb.persistence.dao.SimpleDAO;
import com.excilys.formation.cdb.persistence.impl.ConnectionManagerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public enum SimpleDAOImpl implements SimpleDAO {
    INSTANCE;

    private static final Logger LOG = LoggerFactory.getLogger(SimpleDAOImpl.class);

    private static ConnectionManagerImpl connectionManager = ConnectionManagerImpl.INSTANCE;

    SimpleDAOImpl() {

    }

    public Long count(String query) throws DAOException {
        LOG.debug("count:");
        Connection conn = connectionManager.getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        Long l = null;

        try {
            stmt = conn.createStatement();

            LOG.debug("Executing query \"" + query + "\"");
            rs = stmt.executeQuery(query);
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    l = rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            LOG.error("{}", e);
            throw new DAOException("Couldn't execute the requested COUNT query!");
        } finally {
            ConnectionManagerImpl.closeElements(conn, stmt, rs);
        }

        LOG.debug("Returning " + l);
        return l;
    }

    public Long countElementsWithName(String query, String name) throws DAOException {
        LOG.debug("countElementsWithName");
        Connection conn = connectionManager.getConnection();
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        Long l = null;

        try {
            prepStmt = conn.prepareStatement(query);
            prepStmt.setString(1, "%" + name + "%");

            LOG.debug("Executing query \"" + prepStmt + "\"");
            rs = prepStmt.executeQuery();
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    l = rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            LOG.error("{}", e);
            throw new DAOException("Couldn't execute the requested COUNT LIKE query!");
        } finally {
            ConnectionManagerImpl.closeElements(conn, prepStmt, rs);
        }

        LOG.debug("Returning " + l);
        return l;
    }
}

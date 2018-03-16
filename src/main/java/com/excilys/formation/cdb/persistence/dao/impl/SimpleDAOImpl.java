package com.excilys.formation.cdb.persistence.dao.impl;

import com.excilys.formation.cdb.persistence.ConnectionManager;
import com.excilys.formation.cdb.persistence.dao.SimpleDAO;

import java.sql.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum SimpleDAOImpl implements SimpleDAO {
    INSTANCE;

    private static final Logger LOG = LoggerFactory.getLogger(SimpleDAOImpl.class);

    private static ConnectionManager connectionManager = ConnectionManager.INSTANCE;

    private SimpleDAOImpl() {

    }

    public Long count(String query) {
        LOG.info("SimpleDAOImpl count");
        Connection conn = connectionManager.getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        Long l = null;

        try {
            stmt = conn.createStatement();

            LOG.info("Executing query \"" + query + "\"");
            rs = stmt.executeQuery(query);
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    l = rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            ConnectionManager.closeElements(conn, stmt, rs);
        }

        LOG.info("Result: " + l);
        return l;
    }

    public Long countElementsWithName(String query, String name) {
        LOG.info("SimpleDAOImpl countElementsWithName");
        Connection conn = connectionManager.getConnection();
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        Long l = null;

        try {
            prepStmt = conn.prepareStatement(query);
            prepStmt.setString(1, "%" + name + "%");
            rs = prepStmt.executeQuery();
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    l = rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.closeElements(conn, prepStmt, rs);
        }

        return l;
    }
}

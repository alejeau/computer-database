package com.excilys.formation.cdb.persistence.dao.impl;

import com.excilys.formation.cdb.persistence.ConnectionManager;
import com.excilys.formation.cdb.persistence.dao.SimpleDAO;

import java.sql.*;

public enum SimpleDAOImpl implements SimpleDAO {
    INSTANCE;

    private static ConnectionManager connectionManager = ConnectionManager.INSTANCE;

    private SimpleDAOImpl() {

    }

    public Long count(String query) {
        Connection conn = connectionManager.getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        Long l = null;
        try {
            stmt = conn.createStatement();
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

        return l;
    }

    public Long countElementsWithName(String query, String name) {
        Connection conn = connectionManager.getConnection();
        PreparedStatement prep_stmt = null;
        ResultSet rs = null;
        Long l = null;

        try {
            prep_stmt = conn.prepareStatement(query);
            prep_stmt.setString(1, "%" + name + "%");
            rs = prep_stmt.executeQuery();
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    l = rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.closeElements(conn, prep_stmt, rs);
        }

        return l;
    }
}

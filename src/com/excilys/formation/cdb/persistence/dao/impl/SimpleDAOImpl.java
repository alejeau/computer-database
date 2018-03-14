package com.excilys.formation.cdb.persistence.dao.impl;

import com.excilys.formation.cdb.persistence.ConnectionManager;
import com.excilys.formation.cdb.persistence.dao.SimpleDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public enum SimpleDAOImpl implements SimpleDAO {
    INSTANCE;

    private static ConnectionManager connectionManager = ConnectionManager.INSTANCE;

    private SimpleDAOImpl() {

    }

    public Long select(String query) {
        Connection conn = connectionManager.getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        Long l = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            if (rs.next()) {
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
}

package com.excilys.formation.cdb.persistance.dao.impl;

import com.excilys.formation.cdb.persistance.ConnectionManager;
import com.excilys.formation.cdb.persistance.dao.SimpleDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SimpleDAOImpl implements SimpleDAO {

    private ConnectionManager connectionManager = ConnectionManager.INSTANCE;

    public Integer select(String query) {
        Connection conn = connectionManager.getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        Integer i = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                while (rs.next()) {
                    i = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            ConnectionManager.closeElements(conn, stmt, rs);
        }

        return i;
    }
}

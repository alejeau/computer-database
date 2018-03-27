package com.excilys.formation.cdb.persistence.impl;

import com.excilys.formation.cdb.exceptions.ConnectionException;
import com.excilys.formation.cdb.persistence.ConnectionManager;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public enum HikariCPImpl implements ConnectionManager {
    INSTANCE;
    private static final Logger LOG = LoggerFactory.getLogger(HikariCPImpl.class);
    private static HikariConfig hikariConfig;
    private static HikariDataSource hikariDataSource;

    static {
        hikariConfig = new HikariConfig("properties/db.properties");
        hikariDataSource = new HikariDataSource(hikariConfig);
    }

    HikariCPImpl() {
    }

    @Override
    public Connection getConnection() throws ConnectionException {
        try {
            return hikariDataSource.getConnection();
        } catch (SQLException e) {
            LOG.error("{}", e);
            throw new ConnectionException("Couldn't obtain a connection!", e);
        }
    }

    public static void closeElements(Connection conn, Statement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

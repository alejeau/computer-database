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
    private static final String PROPERTIES_FILE = "/properties/db.properties";
    private HikariDataSource hikariDataSource = new HikariDataSource(new HikariConfig(PROPERTIES_FILE));

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

    @Override
    public void closeElements(Connection connection, Statement statement, ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOG.error("{}", e);
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOG.error("{}", e);
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOG.error("{}", e);
            }
        }
    }
}

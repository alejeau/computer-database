package com.excilys.formation.cdb.persistence.impl;

import com.excilys.formation.cdb.exceptions.ConnectionException;
import com.excilys.formation.cdb.persistence.ConnectionManager;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public enum ConnectionManagerImpl implements ConnectionManager {
    INSTANCE;
    private static final Logger LOG = LoggerFactory.getLogger(ConnectionManagerImpl.class);
    private static final String PROPERTIES_FILE = "properties/db.properties";

    private Properties props = new Properties();
    private HikariConfig hikariConfig = new HikariConfig();
    private HikariDataSource hikariDataSource;

    {
        try {
            props.load(ConnectionManagerImpl.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }

        hikariConfig.setDriverClassName(props.getProperty("datasource.driver-class-name"));
        hikariConfig.setJdbcUrl(props.getProperty("datasource.url"));
        hikariConfig.setUsername(props.getProperty("datasource.username"));
        hikariConfig.setPassword(props.getProperty("datasource.password"));
        hikariConfig.addDataSourceProperty("maximumPoolSize", props.getProperty("datasource.maximumPoolSize"));
        hikariConfig.addDataSourceProperty("cachePrepStmts", props.getProperty("datasource.cachePrepStmts"));
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", props.getProperty("datasource.prepStmtCacheSize"));
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", props.getProperty("datasource.prepStmtCacheSqlLimit"));
        hikariDataSource = new HikariDataSource(hikariConfig);
    }

    ConnectionManagerImpl() {
        String driver = props.getProperty("datasource.driver-class-name");
        try {
            Class.forName(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() throws ConnectionException {
        try {
            return hikariDataSource.getConnection();
        } catch (SQLException e) {
            LOG.error("{}", e);
            throw new ConnectionException("Couldn't get a connection!", e);
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

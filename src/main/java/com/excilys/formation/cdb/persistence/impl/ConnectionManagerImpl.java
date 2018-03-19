package com.excilys.formation.cdb.persistence.impl;

import com.excilys.formation.cdb.persistence.ConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Properties;

public enum ConnectionManagerImpl implements ConnectionManager {
    INSTANCE;

    private Properties props = new Properties();
    private FileInputStream in;

    private String url;
    private String username;
    private String password;

    {
        try {
            in = new FileInputStream("config/db/db.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            props.load(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ConnectionManagerImpl() {
        url = props.getProperty("jdbc.url");
        username = props.getProperty("jdbc.username");
        password = props.getProperty("jdbc.password");
    }

    @Override
    public Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
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

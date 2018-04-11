package com.excilys.formation.cdb.persistence.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOUtils {
    private static final Logger LOG = LoggerFactory.getLogger(DAOUtils.class);

    private DAOUtils() {
    }

    public static void closeElements(Connection connection, Statement statement, ResultSet resultSet) {
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

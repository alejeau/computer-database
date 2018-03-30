package com.excilys.formation.cdb.persistence;

import com.excilys.formation.cdb.exceptions.ConnectionException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public interface ConnectionManager {
    Connection getConnection() throws ConnectionException;

    void closeElements(Connection connection, Statement statement, ResultSet resultSet);
}

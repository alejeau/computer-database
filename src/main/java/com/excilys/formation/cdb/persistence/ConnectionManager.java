package com.excilys.formation.cdb.persistence;

import com.excilys.formation.cdb.exceptions.ConnectionException;

import java.sql.Connection;

public interface ConnectionManager {
    Connection getConnection() throws ConnectionException;
}

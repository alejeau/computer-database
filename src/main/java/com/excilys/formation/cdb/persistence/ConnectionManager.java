package com.excilys.formation.cdb.persistence;

import java.sql.Connection;

public interface ConnectionManager {
    Connection getConnection();
}

package com.excilys.formation.cdb.persistence;

import com.excilys.formation.cdb.exceptions.ConnectionException;
import com.excilys.formation.cdb.persistence.impl.HikariCPImpl;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;

public class ConnectionManagerTest {

    @Test
    public void getConnection() throws ConnectionException {
        ConnectionManager connectionManager = HikariCPImpl.INSTANCE;
        try (Connection conn = connectionManager.getConnection()) {
            assertNotNull(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
package com.excilys.formation.cdb.persistence;

import com.excilys.formation.cdb.exceptions.ConnectionException;
import com.excilys.formation.cdb.persistence.impl.HikariCPImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;

@Component
public class ConnectionManagerTest {
    @Autowired
    private ConnectionManager connectionManager;

    @Test
    public void getConnection() throws ConnectionException {
        try (Connection conn = connectionManager.getConnection()) {
            assertNotNull(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
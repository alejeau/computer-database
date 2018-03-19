package com.excilys.formation.cdb.persistence;

import com.excilys.formation.cdb.persistence.impl.ConnectionManagerImpl;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;

public class ConnectionManagerTest {

    @Test
    public void getConnection() {
        try (Connection conn = ConnectionManagerImpl.INSTANCE.getConnection()) {
            assertNotNull(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
package com.excilys.formation.cdb.persistence;

import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class ConnectionManagerTest {

    @Test
    public void getConnection() {
        try (Connection conn = ConnectionManager.INSTANCE.getConnection()) {
            assertNotNull(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
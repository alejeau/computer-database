package com.excilys.formation.cdb.utils;

import com.excilys.formation.cdb.exceptions.ConnectionException;
import com.excilys.formation.cdb.persistence.ConnectionManager;
import com.excilys.formation.cdb.persistence.dao.impl.DbFields;
import com.excilys.formation.cdb.persistence.impl.HikariCPImpl;
import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class HSQLDatabase {
    private static final ConnectionManager CONNECTION_MANAGER = HikariCPImpl.INSTANCE;

    private static final String DROP_TABLE = "DROP TABLE %s;";
    private static final String DROP_COMPUTER_TABLE = String.format(DROP_TABLE, DbFields.COMPUTER);
    private static final String DROP_COMPANY_TABLE = String.format(DROP_TABLE, DbFields.COMPANY);

    private static final String HSQLDB_FILE = "/hsqldb_init.sql";

    public static void initDatabase() throws SQLException, IOException, ConnectionException {
        try (Connection connection = CONNECTION_MANAGER.getConnection();
             InputStream inputStream = HSQLDatabase.class.getResourceAsStream(HSQLDB_FILE)) {

            SqlFile sqlFile = new SqlFile(new InputStreamReader(inputStream), "init", System.out, "UTF-8", false, new File("."));
            sqlFile.setConnection(connection);
            try {
                sqlFile.execute();
            } catch (SqlToolError e) {
                e.printStackTrace();
            }
        }
    }

    public static void destroy() throws SQLException, ConnectionException {
        try (Connection connection = CONNECTION_MANAGER.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(DROP_COMPUTER_TABLE);
            statement.executeUpdate(DROP_COMPANY_TABLE);
        }
    }
}

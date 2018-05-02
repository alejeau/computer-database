package com.excilys.formation.cdb.utils;

import com.excilys.formation.cdb.config.HibernatePersistenceConfigTest;
import com.excilys.formation.cdb.model.constants.DbFields;
import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Component
@ContextConfiguration(classes = HibernatePersistenceConfigTest.class)
public class HSQLDatabase {
    private DataSource dataSource;

    private static final String DROP_TABLE = "DROP TABLE %s;";
    private static final String DROP_COMPUTER_TABLE = String.format(DROP_TABLE, DbFields.COMPUTER);
    private static final String DROP_COMPANY_TABLE = String.format(DROP_TABLE, DbFields.COMPANY);

    private static final String HSQLDB_FILE = "/hsqldb_init.sql";

    @Autowired
    public HSQLDatabase(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void initDatabase() throws SQLException, IOException {
        try (Connection connection = dataSource.getConnection();
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

    public void destroy() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(DROP_COMPUTER_TABLE);
            statement.executeUpdate(DROP_COMPANY_TABLE);
        }
    }
}

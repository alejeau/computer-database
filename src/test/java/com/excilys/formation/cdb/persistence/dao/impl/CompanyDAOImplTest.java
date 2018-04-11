package com.excilys.formation.cdb.persistence.dao.impl;

import com.excilys.formation.cdb.exceptions.DAOException;
import com.excilys.formation.cdb.utils.HSQLDatabase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/contexts/test-context.xml"})
public class CompanyDAOImplTest {

    @Autowired
    private HSQLDatabase hsqlDatabase;

    @Autowired
    private CompanyDAOImpl companyDAOImpl;

    @Before
    public void setUp() throws SQLException, IOException {
        hsqlDatabase.initDatabase();
    }

    @After
    public void cleanUp() throws SQLException {
        hsqlDatabase.destroy();
    }

    @Test
    public void getComputerIDsWithCompanyID() throws DAOException {
        final Long ID = 1L;
        final List<Long> EXPECTED_LIST = Collections.singletonList(ID);

        List<Long> result = companyDAOImpl.getComputerIDsWithCompanyID(ID);

        assertEquals(EXPECTED_LIST, result);

    }
}
package com.excilys.formation.cdb.persistence.dao.impl;

import com.excilys.formation.cdb.config.TestConfig;
import com.excilys.formation.cdb.exceptions.DAOException;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.paginator.core.LimitValue;
import com.excilys.formation.cdb.persistence.dao.CompanyDAO;
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
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class CompanyDAOTest {
    @Autowired
    private CompanyDAO companyDAO;

    @Autowired
    private HSQLDatabase hsqlDatabase;

    private static final Long INDEX = 0L;
    private static final Long LIMIT = LimitValue.TEN.getValue();
    private static final Long NUMBER_OF_COMPANIES = 3L;
    private static final Long NUMBER_OF_COMPANIES_WITH_NAME_COMPANY = 3L;

    private static final String NAME = "Company";

    private static final Company COMPANY_1 = new Company(1L, "Company 1");
    private static final Company COMPANY_2 = new Company(2L, "Company 2");
    private static final Company COMPANY_3 = new Company(3L, "Company 3");

    private static final List<Company> COMPANY_LIST = Arrays.asList(COMPANY_1, COMPANY_2, COMPANY_3);

    @Before
    public void setUp() throws SQLException, IOException {
        hsqlDatabase.initDatabase();
    }

    @After
    public void cleanUp() throws SQLException {
        hsqlDatabase.destroy();
    }

    @Test
    public void getNumberOfCompanies() throws DAOException {
        assertEquals(NUMBER_OF_COMPANIES, companyDAO.getNumberOfCompanies());
    }

    @Test
    public void getNumberOfCompaniesWithName() throws DAOException {
        assertEquals(NUMBER_OF_COMPANIES_WITH_NAME_COMPANY, companyDAO.getNumberOfCompaniesWithName(NAME));
    }

    @Test
    public void getCompany() throws DAOException {
        assertEquals(COMPANY_1, companyDAO.getCompany(1L));
    }

    @Test
    public void getCompanies() throws DAOException {
        assertEquals(COMPANY_LIST, companyDAO.getCompanies());
    }

    @Test
    public void getCompaniesFromTo() throws DAOException {
        assertEquals(COMPANY_LIST, companyDAO.getCompanies(INDEX, LIMIT));
    }

    @Test
    public void deleteCompany() throws DAOException {
        final Long ID = 2L;
        final Long EXPECTED_LENGTH = 2L;
        companyDAO.deleteCompany(ID);

        assertNull(companyDAO.getCompany(ID));
        assertEquals(EXPECTED_LENGTH, companyDAO.getNumberOfCompanies());
    }
}
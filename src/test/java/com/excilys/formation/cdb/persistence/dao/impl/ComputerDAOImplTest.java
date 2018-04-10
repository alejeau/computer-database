package com.excilys.formation.cdb.persistence.dao.impl;

import com.excilys.formation.cdb.exceptions.ConnectionException;
import com.excilys.formation.cdb.exceptions.DAOException;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.paginator.core.LimitValue;
import com.excilys.formation.cdb.persistence.DatabaseField;
import com.excilys.formation.cdb.persistence.dao.ComputerDAO;
import com.excilys.formation.cdb.utils.HSQLDatabase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@Component
public class ComputerDAOImplTest {
    @Autowired
    private ComputerDAO computerDAO;
    private static final Long INDEX = 0L;
    private static final Long LIMIT = LimitValue.TEN.getValue();
    private static final Long NUMBER_OF_COMPUTERS = 3L;
    private static final Long NUMBER_OF_COMPUTERS_WITH_NAME_COMPUTER = 3L;

    private static final String NAME = "Computer";

    private static final Company COMPANY_1 = new Company(1L, "Company 1");
    private static final Company COMPANY_2 = new Company(2L, "Company 2");
    private static final Company COMPANY_3 = new Company(3L, "Company 3");

    private static final Computer COMPUTER_1 = new Computer.Builder()
            .id(1L)
            .name("Computer 1")
            .introduced("0001-01-01")
            .discontinued("0001-01-02")
            .company(COMPANY_1)
            .build();
    private static final Computer COMPUTER_2 = new Computer.Builder()
            .id(2L)
            .name("Computer 2")
            .build();
    private static final Computer COMPUTER_3 = new Computer.Builder()
            .id(3L)
            .name("Computer 3")
            .introduced("1992-04-13")
            .company(COMPANY_3)
            .build();

    private static final List<Computer> COMPUTER_LIST = Arrays.asList(COMPUTER_1, COMPUTER_2, COMPUTER_3);
    private static final List<Computer> COMPUTER_LIST_INVERTED = Arrays.asList(COMPUTER_3, COMPUTER_2, COMPUTER_1);

    @Before
    public void setUp() throws SQLException, IOException, ConnectionException {
        HSQLDatabase.initDatabase();
    }

    @After
    public void cleanUp() throws SQLException, ConnectionException {
        HSQLDatabase.destroy();
    }

    @Test
    public void getNumberOfComputers() throws DAOException {
        assertEquals(NUMBER_OF_COMPUTERS, computerDAO.getNumberOfComputers());
    }

    @Test
    public void getNumberOfComputersWithName() throws DAOException {
        assertEquals(NUMBER_OF_COMPUTERS_WITH_NAME_COMPUTER, computerDAO.getNumberOfComputersWithName(NAME));
    }

    @Test
    public void getComputer() throws DAOException {
        assertEquals(COMPUTER_1, computerDAO.getComputer(COMPUTER_1.getId()));
    }

    @Test
    public void getComputersWithName() throws DAOException {
        assertEquals(COMPUTER_LIST, computerDAO.getComputersWithName(NAME, INDEX, LIMIT));
    }

    @Test
    public void getComputersWithNameOrderedBy() throws DAOException {
        assertEquals(COMPUTER_LIST, computerDAO.getComputersWithNameOrderedBy(NAME, INDEX, LIMIT, DatabaseField.COMPUTER_NAME, true));
    }

    @Test
    public void getComputerList() throws DAOException {
        assertEquals(COMPUTER_LIST, computerDAO.getComputerList(INDEX, LIMIT));
    }

    @Test
    public void getComputerListOrderedBy() throws DAOException {
        assertEquals(COMPUTER_LIST_INVERTED, computerDAO.getComputerListOrderedBy(INDEX, LIMIT, DatabaseField.COMPUTER_NAME, false));
    }

    @Test
    public void persistComputer() throws DAOException {
        final Computer COMPUTER = new Computer.Builder().name("Computer 4").build();
        Long EXPECTED = 4L;
        assertEquals(EXPECTED, computerDAO.persistComputer(COMPUTER));
    }

    @Test
    public void updateComputer() throws DAOException {
        Computer computer = computerDAO.getComputer(2L);
        computer.setCompany(COMPANY_2);
        computerDAO.updateComputer(computer);
        assertEquals(computer, computerDAO.getComputer(2L));
    }

    @Test
    public void deleteComputer() throws DAOException {
        final Long ID = 2L;
        final Long EXPECTED_LENGTH = 2L;

        computerDAO.deleteComputer(ID);
        assertEquals(EXPECTED_LENGTH, computerDAO.getNumberOfComputers());
        assertNull(computerDAO.getComputer(ID));
    }

    @Test
    public void deleteComputers() throws DAOException {
        final List<Long> ID_LIST = Arrays.asList(COMPUTER_2.getId(), COMPUTER_3.getId());
        final Long EXPECTED_LENGTH = 1L;
        final List<Computer> EXPECTED_LIST = Collections.singletonList(COMPUTER_1);

        computerDAO.deleteComputers(ID_LIST);
        assertEquals(EXPECTED_LENGTH, computerDAO.getNumberOfComputers());
        assertEquals(EXPECTED_LIST, computerDAO.getComputerList(INDEX, LIMIT));
    }
}
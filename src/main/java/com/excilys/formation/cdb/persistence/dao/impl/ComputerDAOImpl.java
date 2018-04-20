package com.excilys.formation.cdb.persistence.dao.impl;

import com.excilys.formation.cdb.exceptions.DAOException;
import com.excilys.formation.cdb.mapper.jdbc.JdbcTComputerMapper;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistence.DatabaseField;
import com.excilys.formation.cdb.persistence.dao.ComputerDAO;
import com.excilys.formation.cdb.persistence.dao.SimpleDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.excilys.formation.cdb.persistence.dao.impl.ComputerDAORequest.DELETE_COMPUTER;
import static com.excilys.formation.cdb.persistence.dao.impl.ComputerDAORequest.DELETE_COMPUTER_WITH_COMPANY_ID;
import static com.excilys.formation.cdb.persistence.dao.impl.ComputerDAORequest.DIRECTION;
import static com.excilys.formation.cdb.persistence.dao.impl.ComputerDAORequest.INSERT_COMPUTER;
import static com.excilys.formation.cdb.persistence.dao.impl.ComputerDAORequest.NUMBER_OF_COMPUTERS;
import static com.excilys.formation.cdb.persistence.dao.impl.ComputerDAORequest.NUMBER_OF_COMPUTERS_WITH_NAME_OR_COMPANY_NAME;
import static com.excilys.formation.cdb.persistence.dao.impl.ComputerDAORequest.ORDER_FIELD;
import static com.excilys.formation.cdb.persistence.dao.impl.ComputerDAORequest.SELECT_ALL_COMPUTERS_ORDERED_BY;
import static com.excilys.formation.cdb.persistence.dao.impl.ComputerDAORequest.SELECT_COMPUTER_BY_ID;
import static com.excilys.formation.cdb.persistence.dao.impl.ComputerDAORequest.SELECT_COMPUTER_BY_NAME_OR_COMPANY_NAME_ORDERED_BY;
import static com.excilys.formation.cdb.persistence.dao.impl.ComputerDAORequest.UPDATE_COMPUTER;

@Repository
public class ComputerDAOImpl implements ComputerDAO {
    private static final Logger LOG = LoggerFactory.getLogger(ComputerDAOImpl.class);

    private static final String ASCENDING = "ASC";
    private static final String DESCENDING = "DESC";

    private JdbcTemplate jdbcTemplate;
    private SimpleDAO simpleDAO;

    ComputerDAOImpl() {
    }

    @Autowired
    public ComputerDAOImpl(SimpleDAO simpleDAO, DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleDAO = simpleDAO;
    }

    @Override
    public Long getNumberOfComputers() throws DAOException {
        LOG.debug("getNumberOfComputers");
        return simpleDAO.count(NUMBER_OF_COMPUTERS);
    }

    @Override
    public Long getNumberOfComputersWithName(String name) throws DAOException {
        LOG.debug("getNumberOfComputersWithName");
        String escapedName = String.format("%%%s%%", name);
        return simpleDAO.countWithStringParameters(NUMBER_OF_COMPUTERS_WITH_NAME_OR_COMPANY_NAME, Arrays.asList(escapedName, escapedName));
    }

    @Override
    public Computer getComputer(Long id) {
        LOG.debug("getComputersWithName");

        Object[] params = new Object[]{id};
        List<Computer> computerList = jdbcTemplate.query(SELECT_COMPUTER_BY_ID, params, new JdbcTComputerMapper());
        if (!computerList.isEmpty()) {
            return computerList.get(0);
        }

        return null;
    }

    @Override
    public List<Computer> getComputersWithName(String name, long index, Long limit) {
        LOG.debug("getComputersWithName");
        return this.getComputersWithNameOrderedBy(name, index, limit, DatabaseField.COMPUTER_NAME, true);
    }

    @Override
    public List<Computer> getComputersWithNameOrderedBy(String name, long index, Long limit, DatabaseField computerField, boolean ascending) {
        LOG.debug("getComputersWithNameOrderedBy");
        final String QUERY = SELECT_COMPUTER_BY_NAME_OR_COMPANY_NAME_ORDERED_BY
                .replace(ORDER_FIELD, computerField.getValue())
                .replace(DIRECTION, ascending ? ASCENDING : DESCENDING);
        LOG.debug("Query = {}", QUERY);
        String escapedName = String.format("%%%s%%", name);
        Object[] params = new Object[]{escapedName, escapedName, index, limit};
        return jdbcTemplate.query(QUERY, params, new JdbcTComputerMapper());
    }

    @Override
    public List<Computer> getComputerList(long index, Long limit) {
        return getComputerListOrderedBy(index, limit, DatabaseField.COMPUTER_NAME, true);
    }

    @Override
    public List<Computer> getComputerListOrderedBy(long index, Long limit, DatabaseField computerField, boolean ascending) {
        LOG.debug("getComputerList");
        final String QUERY = SELECT_ALL_COMPUTERS_ORDERED_BY
                .replace(ORDER_FIELD, computerField.getValue())
                .replace(DIRECTION, ascending ? ASCENDING : DESCENDING);
        LOG.debug("Query = {}", QUERY);

        Object[] params = new Object[]{index, limit};
        return jdbcTemplate.query(QUERY, params, new JdbcTComputerMapper());
    }

    @Override
    public void updateComputer(Computer computer) {
        LOG.debug("updateComputer");
        LOG.debug("Computer: {}", computer);

        Object[] params = new Object[]{
                computer.getName(),
                computer.getIntroduced(),
                computer.getDiscontinued(),
                computer.getCompany() != null ? computer.getCompany().getId() : null,
                computer.getId()
        };
        jdbcTemplate.update(UPDATE_COMPUTER, params);
    }

    @Override
    public Long persistComputer(Computer computer) {
        LOG.debug("persistComputer");
        final PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(final Connection connection) throws SQLException {
                PreparedStatement prepStmt = connection.prepareStatement(INSERT_COMPUTER, Statement.RETURN_GENERATED_KEYS);
                prepStmt.setString(1, computer.getName());
                if (computer.getIntroduced() != null) {
                    prepStmt.setDate(2, Date.valueOf(computer.getIntroduced()));
                } else {
                    prepStmt.setNull(2, Types.DATE);
                }

                if (computer.getDiscontinued() != null) {
                    prepStmt.setDate(3, Date.valueOf(computer.getDiscontinued()));
                } else {
                    prepStmt.setNull(3, Types.DATE);
                }
                if (computer.getCompany() != null && computer.getCompany().getId() != null) {
                    prepStmt.setLong(4, computer.getCompany().getId());
                } else {
                    prepStmt.setNull(4, Types.BIGINT);
                }

                return prepStmt;
            }
        };

        // The newly generated key will be saved in this object
        final KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(psc, holder);
        Number key = holder.getKey();
        return key != null ? key.longValue() : -1L;
    }

    @Override
    public void deleteComputer(Long id) {
        LOG.debug("deleteComputer");
        this.deleteComputers(Collections.singletonList(id));
    }

    @Override
    public void deleteComputers(List<Long> idList) {
        LOG.debug("deleteComputers (by list)");
        idList.forEach(id -> jdbcTemplate.update(DELETE_COMPUTER, id));
    }

    @Override
    public void deleteComputersWhitCompanyId(Long companyId) {
        LOG.debug("deleteComputersWithCompanyId");

        Object[] params = new Object[]{companyId};
        jdbcTemplate.update(DELETE_COMPUTER_WITH_COMPANY_ID, params);
    }
}

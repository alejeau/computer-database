package com.excilys.formation.cdb.persistence.dao.impl;

import com.excilys.formation.cdb.exceptions.DAOException;
import com.excilys.formation.cdb.mapper.jdbcTemplate.JdbcTCompanyMapper;
import com.excilys.formation.cdb.persistence.dao.SimpleDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static com.excilys.formation.cdb.persistence.dao.impl.CompanyDAORequest.ALL_COMPANIES_WITH_LIMIT;

@Repository
public class SimpleDAOImpl implements SimpleDAO {
    private static final Logger LOG = LoggerFactory.getLogger(SimpleDAOImpl.class);

    private JdbcTemplate jdbcTemplate;

    SimpleDAOImpl() {
    }

    @Autowired
    public SimpleDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Long count(final String QUERY) {
        LOG.debug("count: {}", QUERY);
        return jdbcTemplate.queryForObject(QUERY, Long.class);
    }

    @Override
    public Long countElementsWithName(String query, String name) {
        LOG.debug("countElementsWithName");

        Object[] param = new Object[]{"%" + name + "%"};
        return jdbcTemplate.queryForObject(query, param, Long.class);
    }

    public Long countWithStringParameters(String query, List<String> params) throws DAOException {
        LOG.debug("countWithStringParameters");
        Object[] objects = new Object[params.size()];
        for (int i = 0; i < params.size(); i++) {
            objects[i] = params.get(i);
        }
        return jdbcTemplate.queryForObject(query, objects, Long.class);
    }
}

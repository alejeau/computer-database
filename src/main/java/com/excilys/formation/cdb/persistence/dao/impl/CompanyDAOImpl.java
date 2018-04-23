package com.excilys.formation.cdb.persistence.dao.impl;

import com.excilys.formation.cdb.exceptions.DAOException;
import com.excilys.formation.cdb.mapper.jdbc.JdbcTCompanyMapper;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.persistence.dao.CompanyDAO;
import com.excilys.formation.cdb.persistence.dao.SimpleDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

import static com.excilys.formation.cdb.persistence.dao.impl.CompanyDAORequest.ALL_COMPANIES;
import static com.excilys.formation.cdb.persistence.dao.impl.CompanyDAORequest.ALL_COMPANIES_WITH_LIMIT;
import static com.excilys.formation.cdb.persistence.dao.impl.CompanyDAORequest.COMPANY_BY_ID;
import static com.excilys.formation.cdb.persistence.dao.impl.CompanyDAORequest.DELETE_COMPANY_WITH_ID;
import static com.excilys.formation.cdb.persistence.dao.impl.CompanyDAORequest.NUMBER_OF_COMPANIES;
import static com.excilys.formation.cdb.persistence.dao.impl.CompanyDAORequest.NUMBER_OF_COMPANIES_WITH_NAME;

@Repository
public class CompanyDAOImpl implements CompanyDAO {
    private static final Logger LOG = LoggerFactory.getLogger(ComputerDAOImpl.class);

    private JdbcTemplate jdbcTemplate;
    private SimpleDAO simpleDao;

    CompanyDAOImpl() {
    }

    @Autowired
    public CompanyDAOImpl(DataSource dataSource, SimpleDAO simpleDao) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleDao = simpleDao;
    }

    @Override
    public Long getNumberOfCompanies() throws DAOException {
        LOG.debug("getNumberOfCompanies");
        return simpleDao.count(NUMBER_OF_COMPANIES);
    }

    @Override
    public Long getNumberOfCompaniesWithName(String name) throws DAOException {
        LOG.debug("getNumberOfCompaniesWithName");
        return simpleDao.countElementsWithName(NUMBER_OF_COMPANIES_WITH_NAME, name);
    }

    @Override
    public Company getCompany(Long id) {
        LOG.debug("getCompany with id {}", id);

        Object[] params = new Object[]{id};
        List<Company> companyList = jdbcTemplate.query(COMPANY_BY_ID, params, new JdbcTCompanyMapper());
        if (!companyList.isEmpty()) {
            return companyList.get(0);
        }

        return null;
    }

    @Override
    public List<Company> getCompanies() {
        LOG.debug("getCompanies");

        Object[] params = new Object[]{};
        return jdbcTemplate.query(ALL_COMPANIES, params, new JdbcTCompanyMapper());
    }

    @Override
    public List<Company> getCompanies(Long index, Long limit) {
        LOG.debug("getCompanies, index: {}, limit: {}", index, limit);

        Object[] params = new Object[]{index, limit};
        return jdbcTemplate.query(ALL_COMPANIES_WITH_LIMIT, params, new JdbcTCompanyMapper());
    }

    @Override
    public void deleteCompany(Long id) {
        LOG.debug("deleteCompany");

        Object[] params = new Object[]{id};
        jdbcTemplate.update(DELETE_COMPANY_WITH_ID, params);
    }
}

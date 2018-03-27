package com.excilys.formation.cdb.persistence.dao.impl;

import com.excilys.formation.cdb.exceptions.ConnectionException;
import com.excilys.formation.cdb.exceptions.DAOException;
import com.excilys.formation.cdb.mapper.model.CompanyMapper;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.persistence.dao.CompanyDAO;
import com.excilys.formation.cdb.persistence.impl.ConnectionManagerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.excilys.formation.cdb.persistence.dao.impl.DbFields.COMPANY_STAR;

public enum CompanyDAOImpl implements CompanyDAO {
    INSTANCE;
    private static final Logger LOG = LoggerFactory.getLogger(ComputerDAOImpl.class);

    private static ConnectionManagerImpl connectionManager = ConnectionManagerImpl.INSTANCE;

    private static final String NUMBER_OF_COMPANIES = "SELECT COUNT(company_id) FROM company;";
    private static final String NUMBER_OF_COMPANIES_WITH_NAME = "SELECT COUNT(company_id) FROM company WHERE company_name LIKE ?;";
    private static final String COMPANY_BY_ID = "SELECT " + COMPANY_STAR + " FROM company WHERE company_id=?;";
    private static final String COMPANY_BY_NAME = "SELECT " + COMPANY_STAR + " FROM company WHERE company_name LIKE ? ORDER BY company_name LIMIT ?, ?;";
    private static final String ALL_COMPANIES = "SELECT " + COMPANY_STAR + " FROM company ORDER BY company_name;";
    private static final String ALL_COMPANIES_WITH_LIMIT = "SELECT " + COMPANY_STAR + " FROM company ORDER BY company_name LIMIT ?, ?;";

    CompanyDAOImpl() {
    }

    @Override
    public Long getNumberOfCompanies() throws DAOException {
        LOG.debug("getNumberOfCompanies");
        SimpleDAOImpl simpleDao = SimpleDAOImpl.INSTANCE;
        return simpleDao.count(NUMBER_OF_COMPANIES);
    }

    @Override
    public Long getNumberOfCompaniesWithName(String name) throws DAOException {
        LOG.debug("getNumberOfCompaniesWithName");
        SimpleDAOImpl simpleDao = SimpleDAOImpl.INSTANCE;
        return simpleDao.countElementsWithName(NUMBER_OF_COMPANIES_WITH_NAME, name);
    }

    @Override
    public Company getCompany(Long id) throws DAOException {
        LOG.debug("getCompanyName (with id)");
        Connection conn;
        try {
            conn = connectionManager.getConnection();
        } catch (ConnectionException e) {
            LOG.error("{}", e);
            throw new DAOException("Couldn't obtain a connection!", e);
        }
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        Company company;

        try {
            prepStmt = conn.prepareStatement(COMPANY_BY_ID);
            prepStmt.setLong(1, id);

            LOG.debug("Executing query \"" + prepStmt + "\"");
            rs = prepStmt.executeQuery();
            company = CompanyMapper.map(rs);
        } catch (SQLException e) {
            LOG.error("{}", e);
            throw new DAOException("Couldn't get company with ID " + id + "!", e);
        } finally {
            ConnectionManagerImpl.closeElements(conn, prepStmt, rs);
        }

        LOG.debug("Returning " + company);
        return company;
    }

    @Override
    public List<Company> getCompany(String name, Long index, Long limit) throws DAOException {
        LOG.debug("getCompanyName (with name)");
        Connection conn;
        try {
            conn = connectionManager.getConnection();
        } catch (ConnectionException e) {
            LOG.error("{}", e);
            throw new DAOException("Couldn't obtain a connection!", e);
        }
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        List<Company> companies;

        try {
            prepStmt = conn.prepareStatement(COMPANY_BY_NAME);
            prepStmt.setString(1, "%" + name + "%");
            prepStmt.setLong(2, index);
            prepStmt.setLong(3, limit);

            LOG.debug("Executing query \"" + prepStmt + "\"");
            rs = prepStmt.executeQuery();
            companies = CompanyMapper.mapList(rs);
        } catch (SQLException e) {
            LOG.error("{}", e);
            throw new DAOException("Couldn't get list of companies with NAME LIKE " + name + "!", e);
        } finally {
            ConnectionManagerImpl.closeElements(conn, prepStmt, rs);
        }

        LOG.debug("Returning list of size " + companies.size());
        return companies;
    }

    @Override
    public List<Company> getCompanies() throws DAOException {
        LOG.debug("getCompanies");
        Connection conn;
        try {
            conn = connectionManager.getConnection();
        } catch (ConnectionException e) {
            LOG.error("{}", e);
            throw new DAOException("Couldn't obtain a connection!", e);
        }
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        List<Company> companies;

        try {
            prepStmt = conn.prepareStatement(ALL_COMPANIES);

            LOG.debug("Executing query \"" + prepStmt + "\"");
            rs = prepStmt.executeQuery();
            companies = CompanyMapper.mapList(rs);
        } catch (SQLException e) {
            LOG.error("{}", e);
            throw new DAOException("Couldn't get the list of companies!", e);
        } finally {
            ConnectionManagerImpl.closeElements(conn, prepStmt, rs);
        }

        LOG.debug("Returning list of size " + companies.size());
        return companies;
    }

    @Override
    public List<Company> getCompanies(Long index, Long limit) throws DAOException {
        LOG.debug("getCompanies, index" + index, ", limit: " + limit);
        Connection conn;
        try {
            conn = connectionManager.getConnection();
        } catch (ConnectionException e) {
            LOG.error("{}", e);
            throw new DAOException("Couldn't obtain a connection!", e);
        }
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        List<Company> companies;

        try {
            prepStmt = conn.prepareStatement(ALL_COMPANIES_WITH_LIMIT);
            prepStmt.setLong(1, index);
            prepStmt.setLong(2, limit);

            LOG.debug("Executing query \"" + prepStmt + "\"");
            rs = prepStmt.executeQuery();
            companies = CompanyMapper.mapList(rs);
        } catch (SQLException e) {
            LOG.error("{}", e);
            throw new DAOException("Couldn't get list of companies from " + index + " to " + limit + "!", e);
        } finally {
            ConnectionManagerImpl.closeElements(conn, prepStmt, rs);
        }

        LOG.debug("Returning list of size " + companies.size());
        return companies;
    }
}

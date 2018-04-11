package com.excilys.formation.cdb.persistence.dao.impl;

import com.excilys.formation.cdb.exceptions.DAOException;
import com.excilys.formation.cdb.exceptions.MapperException;
import com.excilys.formation.cdb.mapper.model.CompanyMapper;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.persistence.dao.CompanyDAO;
import com.excilys.formation.cdb.persistence.dao.ComputerDAO;
import com.excilys.formation.cdb.persistence.dao.SimpleDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.excilys.formation.cdb.persistence.dao.impl.CompanyDAORequest.ALL_COMPANIES;
import static com.excilys.formation.cdb.persistence.dao.impl.CompanyDAORequest.ALL_COMPANIES_WITH_LIMIT;
import static com.excilys.formation.cdb.persistence.dao.impl.CompanyDAORequest.COMPANY_BY_ID;
import static com.excilys.formation.cdb.persistence.dao.impl.CompanyDAORequest.COMPANY_BY_NAME;
import static com.excilys.formation.cdb.persistence.dao.impl.CompanyDAORequest.COMPUTER_IDS_WITH_COMPANY_ID;
import static com.excilys.formation.cdb.persistence.dao.impl.CompanyDAORequest.DELETE_COMPANY_WITH_ID;
import static com.excilys.formation.cdb.persistence.dao.impl.CompanyDAORequest.NUMBER_OF_COMPANIES;
import static com.excilys.formation.cdb.persistence.dao.impl.CompanyDAORequest.NUMBER_OF_COMPANIES_WITH_NAME;

@Repository
public class CompanyDAOImpl implements CompanyDAO {
    private static final Logger LOG = LoggerFactory.getLogger(ComputerDAOImpl.class);

    private DataSource dataSource;
    private SimpleDAO simpleDao;
    private ComputerDAO computerDAO;

    CompanyDAOImpl() {
    }

    @Autowired
    public CompanyDAOImpl(SimpleDAO simpleDao, ComputerDAO computerDAO, DataSource dataSource) {
        this.simpleDao = simpleDao;
        this.computerDAO = computerDAO;
        this.dataSource = dataSource;
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
    public Company getCompany(Long id) throws DAOException {
        LOG.debug("getCompanyName (with id)");
        Connection conn = this.getConnection();
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        Company company;

        try {
            prepStmt = conn.prepareStatement(COMPANY_BY_ID);
            prepStmt.setLong(1, id);

            LOG.debug("Executing query \"{}\"", prepStmt);
            rs = prepStmt.executeQuery();
            try {
                company = CompanyMapper.map(rs);
            } catch (MapperException e) {
                LOG.error("{}", e);
                throw new DAOException("Error while mapping the ResultSet!", e);
            }
        } catch (SQLException e) {
            LOG.error("{}", e);
            throw new DAOException("Couldn't get company with ID " + id + "!", e);
        } finally {
            DAOUtils.closeElements(conn, prepStmt, rs);
        }

        LOG.debug("Returning {}", company);
        return company;
    }

    @Override
    public List<Company> getCompaniesWithName(String name, Long index, Long limit) throws DAOException {
        LOG.debug("getCompanyName (with name)");
        Connection conn = this.getConnection();
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        List<Company> companies;

        try {
            prepStmt = conn.prepareStatement(COMPANY_BY_NAME);
            prepStmt.setString(1, "%" + name + "%");
            prepStmt.setLong(2, index);
            prepStmt.setLong(3, limit);

            LOG.debug("Executing query \"{}\"", prepStmt);
            rs = prepStmt.executeQuery();
            try {
                companies = CompanyMapper.mapList(rs);
            } catch (MapperException e) {
                LOG.error("{}", e);
                throw new DAOException("Error while mapping the ResultSet!", e);
            }
        } catch (SQLException e) {
            LOG.error("{}", e);
            throw new DAOException("Couldn't get list of companies with NAME LIKE " + name + "!", e);
        } finally {
            DAOUtils.closeElements(conn, prepStmt, rs);
        }

        LOG.debug("Returning list of size {}", companies.size());
        return companies;
    }

    @Override
    public List<Company> getCompanies() throws DAOException {
        LOG.debug("getCompanies");
        Connection conn = this.getConnection();
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        List<Company> companies;

        try {
            prepStmt = conn.prepareStatement(ALL_COMPANIES);

            LOG.debug("Executing query \"{}\"", prepStmt);
            rs = prepStmt.executeQuery();
            try {
                companies = CompanyMapper.mapList(rs);
            } catch (MapperException e) {
                LOG.error("{}", e);
                throw new DAOException("Error while mapping the ResultSet!", e);
            }
        } catch (SQLException e) {
            LOG.error("{}", e);
            throw new DAOException("Couldn't get the list of companies!", e);
        } finally {
            DAOUtils.closeElements(conn, prepStmt, rs);
        }

        LOG.debug("Returning list of size {}", companies.size());
        return companies;
    }

    @Override
    public List<Company> getCompanies(Long index, Long limit) throws DAOException {
        LOG.debug("getCompanies, index: {}, limit: {}", index, limit);
        Connection conn = this.getConnection();
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        List<Company> companies;

        try {
            prepStmt = conn.prepareStatement(ALL_COMPANIES_WITH_LIMIT);
            prepStmt.setLong(1, index);
            prepStmt.setLong(2, limit);

            LOG.debug("Executing query \"{}\"", prepStmt);
            rs = prepStmt.executeQuery();
            try {
                companies = CompanyMapper.mapList(rs);
            } catch (MapperException e) {
                LOG.error("{}", e);
                throw new DAOException("Error while mapping the ResultSet!", e);
            }
        } catch (SQLException e) {
            LOG.error("{}", e);
            throw new DAOException("Couldn't get list of companies from " + index + " to " + limit + "!", e);
        } finally {
            DAOUtils.closeElements(conn, prepStmt, rs);
        }

        LOG.debug("Returning list of size {}", companies.size());
        return companies;
    }

    @Override
    public void deleteCompany(Long id) throws DAOException {
        LOG.debug("deleteCompany");
        List<Long> idList = this.getComputerIDsWithCompanyID(id);
        Connection connection = this.getConnection();
        PreparedStatement prepStmt = null;

        try {
            connection.setAutoCommit(false);
            computerDAO.deleteComputers(idList, connection);

            prepStmt = connection.prepareStatement(DELETE_COMPANY_WITH_ID);
            prepStmt.setLong(1, id);
            prepStmt.executeUpdate();

            connection.commit();
        } catch (SQLException e1) {
            try {
                connection.rollback();
            } catch (SQLException e2) {
                LOG.error("{}", e2);
                throw new DAOException("An error occurred while rolling back the changes!");
            }

            LOG.error("{}", e1);
            throw new DAOException("Couldn't delete the supplied list of computers.", e1);
        } finally {
            DAOUtils.closeElements(connection, prepStmt, null);
        }
    }

    List<Long> getComputerIDsWithCompanyID(Long id) throws DAOException {
        LOG.debug("getComputerIDsWithCompanyID");
        Connection conn = this.getConnection();

        List<Long> idList = new ArrayList<>();
        PreparedStatement prepStmt = null;
        ResultSet rs = null;

        try {
            prepStmt = conn.prepareStatement(COMPUTER_IDS_WITH_COMPANY_ID);
            prepStmt.setLong(1, id);

            LOG.debug("Executing query \"{}\"", prepStmt);

            rs = prepStmt.executeQuery();

            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    idList.add(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            LOG.error("{}", e);
            throw new DAOException("Error (PreparedStatement) while fetching computer id list with company id " + id + "!");
        } finally {
            DAOUtils.closeElements(conn, prepStmt, rs);
        }

        LOG.debug("Returning list of size {}", idList.size());
        return idList;
    }

    private Connection getConnection() throws DAOException {
        Connection conn;
        try {
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            LOG.error("{}", e);
            throw new DAOException("Couldn't obtain a connection!", e);
        }
        return conn;
    }
}

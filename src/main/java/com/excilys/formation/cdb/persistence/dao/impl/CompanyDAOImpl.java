package com.excilys.formation.cdb.persistence.dao.impl;

import com.excilys.formation.cdb.mapper.CompanyMapper;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.persistence.ConnectionManager;
import com.excilys.formation.cdb.persistence.dao.CompanyDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.excilys.formation.cdb.persistence.dao.impl.DbFields.COMPANY_STAR;

public enum CompanyDAOImpl implements CompanyDAO {
    INSTANCE;

    private static ConnectionManager connectionManager = ConnectionManager.INSTANCE;

    private static final String NUMBER_OF_COMPANIES = "SELECT COUNT(company_id) FROM company;";
    private static final String NUMBER_OF_COMPANIES_WITH_NAME = "SELECT COUNT(company_id) FROM company WHERE company_name LIKE ?;";
    private static final String COMPANY_BY_ID = "SELECT " + COMPANY_STAR + " FROM company WHERE company_id=?;";
    private static final String COMPANY_BY_NAME = "SELECT " + COMPANY_STAR + " FROM company WHERE company_name LIKE ? ORDER BY company_name LIMIT ?, ?;";
    private static final String ALL_COMPANIES = "SELECT " + COMPANY_STAR + " FROM company ORDER BY company_name LIMIT ?, ?;";

    CompanyDAOImpl() {

    }

    @Override
    public Long getNumberOfCompanies() {
        SimpleDAOImpl simpleDao = SimpleDAOImpl.INSTANCE;
        return simpleDao.count(NUMBER_OF_COMPANIES);
    }

    @Override
    public Long getNumberOfCompaniesWithName(String name) {
        SimpleDAOImpl simpleDao = SimpleDAOImpl.INSTANCE;
        return simpleDao.countElementsWithName(NUMBER_OF_COMPANIES_WITH_NAME, name);
    }

    @Override
    public Company getCompany(Long id) {
        Connection conn = connectionManager.getConnection();
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        Company c = null;

        try {
            prepStmt = conn.prepareStatement(COMPANY_BY_ID);
            prepStmt.setLong(1, id);
            rs = prepStmt.executeQuery();
            c = CompanyMapper.map(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.closeElements(conn, prepStmt, rs);
        }

        return c;
    }

    @Override
    public List<Company> getCompany(String name, int index, int limit) {
        Connection conn = connectionManager.getConnection();
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        List<Company> companies = new ArrayList<>();

        try {
            prepStmt = conn.prepareStatement(COMPANY_BY_NAME);
            prepStmt.setString(1, "%" + name + "%");
            prepStmt.setInt(2, index);
            prepStmt.setInt(3, limit);
            rs = prepStmt.executeQuery();
            companies = CompanyMapper.mapList(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.closeElements(conn, prepStmt, rs);
        }

        return companies;
    }

    @Override
    public List<Company> getCompanies(int index, int limit) {
        Connection conn = connectionManager.getConnection();
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        List<Company> companies = new ArrayList<>();

        try {
            prepStmt = conn.prepareStatement(ALL_COMPANIES);
            prepStmt.setInt(1, index);
            prepStmt.setInt(2, limit);
            rs = prepStmt.executeQuery();
            companies = CompanyMapper.mapList(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.closeElements(conn, prepStmt, rs);
        }

        return companies;
    }
}

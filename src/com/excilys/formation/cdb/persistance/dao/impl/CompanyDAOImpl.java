package com.excilys.formation.cdb.persistance.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.cdb.mapper.CompanyMapper;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.persistance.ConnectionManager;
import com.excilys.formation.cdb.persistance.dao.CompanyDAO;

public class CompanyDAOImpl implements CompanyDAO {

	private ConnectionManager connectionManager = ConnectionManager.INSTANCE;
	
	private static final String NUMBER_OF_COMPANIES = "SELECT COUNT(id) FROM company";
    private static final String COMPANY_BY_ID = "SELECT * FROM company WHERE id=?";
    private static final String COMPANY_BY_NAME = "SELECT * FROM company WHERE name LIKE ? ORDER BY name LIMIT ?, ?";
    private static final String ALL_COMPANIES = "SELECT * FROM company ORDER BY name LIMIT ?, ?";

	@Override
	public int getNumberOfCompanies() {
        SimpleDAOImpl simpleDao = new SimpleDAOImpl();
        return simpleDao.select(NUMBER_OF_COMPANIES);
	}

	@Override
	public Company getCompany(int id) {
        Connection conn = connectionManager.getConnection();
        PreparedStatement prep_stmt = null;
        ResultSet rs = null;
        Company c = null;

        try {
            prep_stmt = conn.prepareStatement(COMPANY_BY_ID);
            prep_stmt.setInt(1, id);
            rs = prep_stmt.executeQuery();
            c = CompanyMapper.mapElement(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.closeElements(conn, prep_stmt, rs);
        }

        return c;
	}

	@Override
	public List<Company> getCompany(String name, int index, int offset) {
        Connection conn = connectionManager.getConnection();
        PreparedStatement prep_stmt = null;
        ResultSet rs = null;
        List<Company> companies = new ArrayList<>();

        try {
            prep_stmt = conn.prepareStatement(COMPANY_BY_NAME);
            prep_stmt.setString(1, "%" + name + "%");
            prep_stmt.setInt(2, index);
            prep_stmt.setInt(3, offset);
            rs = prep_stmt.executeQuery();
            companies = CompanyMapper.mapListOfElements(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.closeElements(conn, prep_stmt, rs);
        }

        return companies;
	}

	@Override
	public List<Company> getCompanies(int index, int offset) {
        Connection conn = connectionManager.getConnection();
        PreparedStatement prep_stmt = null;
        ResultSet rs = null;
        List<Company> companies = new ArrayList<>();

        try {
            prep_stmt = conn.prepareStatement(ALL_COMPANIES);
            prep_stmt.setInt(1, index);
            prep_stmt.setInt(2, offset);
            rs = prep_stmt.executeQuery();
            companies = CompanyMapper.mapListOfElements(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.closeElements(conn, prep_stmt, rs);
        }

        return companies;
	}
}

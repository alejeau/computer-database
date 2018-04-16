package com.excilys.formation.cdb.mapper.jdbcTemplate;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.persistence.dao.impl.DbFields;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTCompanyMapper implements RowMapper<Company> {

    @Override
    public Company mapRow(ResultSet resultSet, int i) throws SQLException {
        Company company = new Company();
        company.setId(resultSet.getLong(DbFields.COMPANY_ID));
        company.setName(resultSet.getString(DbFields.COMPANY_NAME));
        return company;
    }
}

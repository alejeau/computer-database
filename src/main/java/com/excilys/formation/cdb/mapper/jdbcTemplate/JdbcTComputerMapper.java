package com.excilys.formation.cdb.mapper.jdbcTemplate;

import com.excilys.formation.cdb.mapper.model.CompanyMapper;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistence.dao.impl.DbFields;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class JdbcTComputerMapper implements RowMapper<Computer> {
    @Override
    public Computer mapRow(ResultSet resultSet, int i) throws SQLException {
        Computer computer = new Computer();
        computer.setId(resultSet.getLong(DbFields.COMPUTER_ID));
        computer.setName(resultSet.getString(DbFields.COMPUTER_NAME));

        Date date1 = resultSet.getDate(DbFields.COMPUTER_INTRODUCED);
        LocalDate localDate1 = (date1 != null) ? date1.toLocalDate() : null;
        computer.setIntroduced(localDate1);

        date1 = resultSet.getDate(DbFields.COMPUTER_DISCONTINUED);
        localDate1 = (date1 != null) ? date1.toLocalDate() : null;
        computer.setDiscontinued(localDate1);

        computer.setCompany(CompanyMapper.mapFromComputer(resultSet));

        return computer;
    }
}

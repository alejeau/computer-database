package com.excilys.formation.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import com.excilys.formation.cdb.model.Computer;

public class ComputerMapper {

	public static Computer map(ResultSet rs) {
		Computer c = null;
		try {
		    if (rs.isBeforeFirst())
		        c = new Computer();
			while (rs.next()) {
				c.setId(rs.getLong("id"));
				c.setName(rs.getString("name"));
				c.setCompanyId(rs.getLong("company_id"));

                Date date1 = rs.getDate("introduced");
                LocalDate localDate1 = date1 != null ? date1.toLocalDate() : null;
				c.setIntroduced(localDate1);

                date1 = rs.getDate("discontinued");
                localDate1 = date1 != null ? date1.toLocalDate() : null;
                c.setDiscontinued(localDate1);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}

	public static List<Computer> mapList(ResultSet rs) {
		List<Computer> computers = null;
		try {
		    if (rs.isBeforeFirst())
                computers = new ArrayList<>();
			while (rs.next()) {
                Computer c = new Computer();
                c.setId(rs.getLong("id"));
                c.setName(rs.getString("name"));
                c.setCompanyId(rs.getLong("company_id"));

                Date date1 = rs.getDate("introduced");
                LocalDate localDate1 = date1 != null ? date1.toLocalDate() : null;
                c.setIntroduced(localDate1);

                date1 = rs.getDate("discontinued");
                localDate1 = date1 != null ? date1.toLocalDate() : null;
                c.setDiscontinued(localDate1);
				computers.add(c);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return computers;
	}
}

package com.excilys.formation.cdb.mapper.model;

import com.excilys.formation.cdb.dto.model.ComputerDTO;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.DatePattern;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ComputerMapper {

    public static ComputerDTO toDTO(Computer c) {
        ComputerDTO cdto = new ComputerDTO();
        cdto.setId(c.getId());
        cdto.setName(c.getName());
        if (c.getIntroduced() != null) {
            cdto.setIntroduced(c.getIntroduced().format(DatePattern.FORMATTER));
        }
        if (c.getDiscontinued() != null) {
            cdto.setDiscontinued(c.getDiscontinued().format(DatePattern.FORMATTER));
        }
        cdto.setCompanyName(c.getCompany().getName());
        return cdto;
    }

    public static Computer toComputer(ComputerDTO cdto, Company company) {
        return new Computer.Builder()
                .id(cdto.getId())
                .name(cdto.getName())
                .introduced(cdto.getIntroduced())
                .discontinued(cdto.getDiscontinued())
                .company(company)
                .build();
    }

    public static Computer map(ResultSet rs) {
        Computer c = null;
        try {
            if (rs.isBeforeFirst()) {
                c = new Computer();
            }
            while (rs.next()) {
                c.setId(rs.getLong("computer_id"));
                c.setName(rs.getString("computer_name"));

                Date date1 = rs.getDate("computer_introduced");
                LocalDate localDate1 = date1 != null ? date1.toLocalDate() : null;
                c.setIntroduced(localDate1);

                date1 = rs.getDate("computer_discontinued");
                localDate1 = date1 != null ? date1.toLocalDate() : null;
                c.setDiscontinued(localDate1);

                c.setCompany(CompanyMapper.mapFromComputer(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    public static List<Computer> mapList(ResultSet rs) {
        List<Computer> computers = null;
        try {
            if (rs.isBeforeFirst()) {
                computers = new ArrayList<>();
            }
            while (rs.next()) {
                Computer c = new Computer();
                c.setId(rs.getLong("computer_id"));
                c.setName(rs.getString("computer_name"));

                Date date1 = rs.getDate("computer_introduced");
                LocalDate localDate1 = date1 != null ? date1.toLocalDate() : null;
                c.setIntroduced(localDate1);

                date1 = rs.getDate("computer_discontinued");
                localDate1 = date1 != null ? date1.toLocalDate() : null;
                c.setDiscontinued(localDate1);

                c.setCompany(CompanyMapper.mapFromComputer(rs));

                computers.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return computers;
    }
}

package com.excilys.formation.cdb.mapper;

import com.excilys.formation.cdb.model.Company;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyMapper {

    public static Company map(ResultSet rs) {
        Company c = new Company();
        try {
            while (rs.next()) {
                c.setId(rs.getInt("id"));
                c.setName(rs.getString("name"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    public static List<Company> mapList(ResultSet rs) {
        List<Company> companies = new ArrayList<>();
        try {
            while (rs.next()) {
                Company c = new Company();
                c.setId(rs.getInt("id"));
                c.setName(rs.getString("name"));
                companies.add(c);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return companies;
    }

}

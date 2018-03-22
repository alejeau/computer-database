package com.excilys.formation.cdb.mapper.model;

import com.excilys.formation.cdb.dto.model.CompanyDTO;
import com.excilys.formation.cdb.model.Company;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public enum CompanyMapper {
    INSTANCE;

    CompanyMapper() {

    }

    public static CompanyDTO toDTO(Company company) {
        return new CompanyDTO(company.getId(), company.getName());
    }

    public static Company toCompany(CompanyDTO companyDTO) {
        return new Company(companyDTO.getId(), companyDTO.getName());
    }

    public static Company map(ResultSet rs) {
        Company c = new Company();
        try {
            while (rs.next()) {
                c.setId(rs.getLong("company_id"));
                c.setName(rs.getString("company_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    public static Company mapFromComputer(ResultSet rs) throws SQLException {
        Company c = new Company();
        c.setId(rs.getLong("company_id"));
        c.setName(rs.getString("company_name"));
        return c;
    }

    public static List<Company> mapList(ResultSet rs) {
        List<Company> companies = new ArrayList<>();
        try {
            while (rs.next()) {
                Company c = new Company();
                c.setId(rs.getLong("company_id"));
                c.setName(rs.getString("company_name"));
                companies.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return companies;
    }

    public static List<CompanyDTO> mapList(List<Company> companyList) {
        List<CompanyDTO> companyDTOList = new ArrayList<>();
        companyList.forEach(company -> companyDTOList.add(CompanyMapper.toDTO(company)));
        return companyDTOList;
    }


}

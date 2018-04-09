package com.excilys.formation.cdb.mapper.model;

import com.excilys.formation.cdb.dto.model.CompanyDTO;
import com.excilys.formation.cdb.exceptions.MapperException;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.persistence.dao.impl.DbFields;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyMapper {
    private static final Logger LOG = LoggerFactory.getLogger(CompanyMapper.class);

    private CompanyMapper() {
    }

    public static CompanyDTO toDTO(Company company) {
        return new CompanyDTO(company.getId(), company.getName());
    }

    public static Company toCompany(CompanyDTO companyDTO) {
        return new Company(companyDTO.getId(), companyDTO.getName());
    }

    public static Company map(ResultSet rs) throws MapperException {
        Company c = null;
        try {
            if (rs.isBeforeFirst()) {
                c = new Company();
                while (rs.next()) {
                    c.setId(rs.getLong(DbFields.COMPANY_ID));
                    c.setName(rs.getString(DbFields.COMPANY_NAME));
                }
            }
        } catch (SQLException e) {
            LOG.error("{}", e);
            throw new MapperException("Couldn't map the ResultSet to a Company!", e);
        }
        return c;
    }

    public static Company mapFromComputer(ResultSet rs) throws SQLException {
        Company c = new Company();
        c.setId(rs.getLong(DbFields.COMPANY_ID));
        c.setName(rs.getString(DbFields.COMPANY_NAME));
        return c;
    }

    public static List<Company> mapList(ResultSet rs) throws MapperException {
        List<Company> companies = new ArrayList<>();
        try {
            while (rs.next()) {
                Company c = new Company();
                c.setId(rs.getLong(DbFields.COMPANY_ID));
                c.setName(rs.getString(DbFields.COMPANY_NAME));
                companies.add(c);
            }
        } catch (SQLException e) {
            LOG.error("{}", e);
            throw new MapperException("Couldn't map the ResultSet to a list of Company!", e);
        }
        return companies;
    }

    public static List<CompanyDTO> mapList(List<Company> companyList) {
        List<CompanyDTO> companyDTOList = new ArrayList<>();
        companyList.stream()
                .map(CompanyMapper::toDTO)
                .forEach(companyDTOList::add);
        return companyDTOList;
    }
}

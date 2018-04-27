package com.excilys.formation.cdb.mapper.model;

import com.excilys.formation.cdb.dto.model.CompanyDTO;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.persistence.dao.impl.DbFields;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CompanyMapper {

    private CompanyMapper() {
    }

    public static CompanyDTO toDTO(Company company) {
        return new CompanyDTO(company.getId(), company.getName());
    }

    public static Company mapFromComputer(ResultSet rs) throws SQLException {
        Company c = new Company();
        c.setId(rs.getLong(DbFields.COMPANY_ID));
        c.setName(rs.getString(DbFields.COMPANY_NAME));
        return c;
    }

    public static List<CompanyDTO> mapList(List<Company> companyList) {
        List<CompanyDTO> companyDTOList = new ArrayList<>();
        companyList.stream()
                .map(CompanyMapper::toDTO)
                .forEach(companyDTOList::add);
        return companyDTOList;
    }
}

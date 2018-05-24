package com.excilys.formation.cdb.mapper.model;

import com.excilys.formation.cdb.dto.model.CompanyDTO;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.constants.DbFields;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class CompanyMapper {

    private CompanyMapper() {
    }

    public static CompanyDTO toDTO(Company company) {
        if (company == null) {
            return null;
        }
        return new CompanyDTO(company.getId(), company.getName(), company.getPictureUrl(), company.getDescription());
    }

    public static Company toCompany(CompanyDTO companyDTO) {
        if (companyDTO == null) {
            return null;
        }
        return new Company(companyDTO.getId(), companyDTO.getName(), companyDTO.getPictureUrl(), companyDTO.getDescription());
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

    public static List<CompanyDTO> toCompanyDtoList(List<Company> companyList) {
        if (companyList == null) {
            return new ArrayList<>();
        }

        int nonNullObjects = Long.valueOf(companyList.stream().filter(Objects::nonNull).count()).intValue();
        List<CompanyDTO> CompanyDTOList = new ArrayList<>(nonNullObjects);
        companyList
                .stream()
                .filter(Objects::nonNull)
                .map(CompanyMapper::toDTO)
                .forEach(CompanyDTOList::add);

        return CompanyDTOList;
    }
}

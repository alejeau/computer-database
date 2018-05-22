package com.excilys.formation.cdb.rest;

import com.excilys.formation.cdb.dto.model.CompanyDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CompanyRest {
    ResponseEntity<Long> getNumberOfCompanies();

    ResponseEntity<Long> getNumberOfCompaniesWithName(String name);

    ResponseEntity<CompanyDTO> getCompanyWithId(Long id);

    ResponseEntity<List<CompanyDTO>> getCompanyList();

    ResponseEntity<List<CompanyDTO>> getCompanyList(Long index, Long limit);

    ResponseEntity<List<CompanyDTO>> getCompaniesWithName(String name, Long index, Long limit);

    ResponseEntity<Long> persistCompany(CompanyDTO companyDTO);

    ResponseEntity<Boolean> updateCompany(CompanyDTO companyDTO);

    ResponseEntity<Boolean> deleteCompany(Long id);
}

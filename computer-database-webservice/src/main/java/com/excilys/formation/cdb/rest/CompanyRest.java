package com.excilys.formation.cdb.rest;

import com.excilys.formation.cdb.dto.model.CompanyDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CompanyRest {
    ResponseEntity<Long> getNumberOfCompanies();

    ResponseEntity<CompanyDTO> getCompanyWithId(Long id);

    ResponseEntity<List<CompanyDTO>> getCompanies();

    ResponseEntity<List<CompanyDTO>> getCompanyList(Long index, Long limit);

    ResponseEntity<Boolean> deleteCompany(Long id);
}

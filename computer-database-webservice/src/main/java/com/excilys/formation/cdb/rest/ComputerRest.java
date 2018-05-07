package com.excilys.formation.cdb.rest;

import com.excilys.formation.cdb.model.Company;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ComputerRest {
    ResponseEntity<Long> getNumberOfCompanies();

    ResponseEntity<Company> getCompanyWithId(Long id);

    ResponseEntity<List<Company>> getCompanies();

    ResponseEntity<List<Company>> getCompanyList(Long index, Long limit);
}

package com.excilys.formation.cdb.rest;

import com.excilys.formation.cdb.dto.model.ComputerDTO;
import com.excilys.formation.cdb.exceptions.ValidationException;
import com.excilys.formation.cdb.persistence.DatabaseField;
import com.excilys.formation.cdb.service.validators.core.Error;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ComputerRest {

    ResponseEntity<Long> getNumberOfComputers();

    ResponseEntity<Long> getNumberOfComputersWithName(String name);

    ResponseEntity<ComputerDTO> getComputer(Long id);

    ResponseEntity<List<ComputerDTO>> getComputer(String name, long index, Long limit);

    ResponseEntity<List<ComputerDTO>> getComputerOrderedBy(String name, long index, Long limit, DatabaseField computerField, boolean ascending);

    ResponseEntity<List<ComputerDTO>> getComputers(long index, Long limit);

    ResponseEntity<List<ComputerDTO>> getComputersOrderedBy(long index, Long limit, DatabaseField computerField, boolean ascending);

    ResponseEntity<List<Error>> updateComputer(ComputerDTO computerDTO) throws ValidationException;

    ResponseEntity<Long> persistComputer(ComputerDTO computerDTO) throws ValidationException;

    ResponseEntity<Boolean> deleteComputer(Long id);

    ResponseEntity<Boolean> deleteComputers(List<Long> idList);

    ResponseEntity<Boolean> deleteComputersWithCompanyId(Long companyId);
}

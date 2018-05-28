package com.excilys.formation.cdb.rest.impl;

import com.excilys.formation.cdb.dto.model.ComputerDTO;
import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.exceptions.ValidationException;
import com.excilys.formation.cdb.mapper.model.ComputerMapper;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.constants.Paths;
import com.excilys.formation.cdb.persistence.DatabaseField;
import com.excilys.formation.cdb.rest.ComputerRest;
import com.excilys.formation.cdb.service.CompanyService;
import com.excilys.formation.cdb.service.ComputerService;
import com.excilys.formation.cdb.service.validators.ComputerValidator;
import com.excilys.formation.cdb.service.validators.core.Error;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(Paths.REST_COMPUTER)
public class ComputerRestController implements ComputerRest {
    private static final Logger LOG = LoggerFactory.getLogger(ComputerRestController.class);

    private ComputerService computerService;
    private CompanyService companyService;

    @Autowired
    public ComputerRestController(ComputerService computerService) {
        this.computerService = computerService;
    }

    @Autowired
    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Override
    @GetMapping("/total")
    public ResponseEntity<Long> getNumberOfComputers() {
        Long numberOfComputers = null;
        try {
            numberOfComputers = computerService.getNumberOfComputers();
        } catch (ServiceException e) {
            LOG.error("{}", e);
        }
        return ResponseEntityMapper.toResponseEntity(numberOfComputers);
    }

    @Override
    @GetMapping("/name/{name}/total")
    public ResponseEntity<Long> getNumberOfComputersWithName(@PathVariable String name) {
        Long numberOfComputers = null;
        try {
            numberOfComputers = computerService.getNumberOfComputersWithName(name);
        } catch (ServiceException e) {
            LOG.error("{}", e);
        }
        return ResponseEntityMapper.toResponseEntity(numberOfComputers);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ComputerDTO> getComputer(@PathVariable Long id) {
        Computer computer = null;
        try {
            computer = computerService.getComputer(id);
        } catch (ServiceException e) {
            LOG.error("{}", e);
        }
        return ResponseEntityMapper.toResponseEntity(ComputerMapper.toDTO(computer));
    }

    @Override
    @GetMapping("/name/{name}/index/{index}/limit/{limit}")
    public ResponseEntity<List<ComputerDTO>> getComputer(@PathVariable String name, @PathVariable long index, @PathVariable Long limit) {
        List<Computer> computerList = null;
        try {
            computerList = computerService.getComputerOrderedBy(name, index, limit, DatabaseField.COMPUTER_NAME, true);
        } catch (ServiceException e) {
            LOG.error("{}", e);
        }
        return ResponseEntityMapper.toListResponseEntity(ComputerMapper.toComputerDtoList(computerList));
    }

    @Override
    @GetMapping("/name/{name}/index/{index}/limit/{limit}/field/{computerField}/asc/{ascending}")
    public ResponseEntity<List<ComputerDTO>> getComputerOrderedBy(@PathVariable String name, @PathVariable long index, @PathVariable Long limit, @PathVariable DatabaseField computerField, @PathVariable boolean ascending) {
        List<Computer> computerList = null;
        try {
            computerList = computerService.getComputerOrderedBy(name, index, limit, computerField, ascending);
        } catch (ServiceException e) {
            LOG.error("{}", e);
        }
        return ResponseEntityMapper.toListResponseEntity(ComputerMapper.toComputerDtoList(computerList));
    }

    @Override
    @GetMapping("/index/{index}/limit/{limit}")
    public ResponseEntity<List<ComputerDTO>> getComputerList(@PathVariable long index, @PathVariable Long limit) {
        List<Computer> computerList = null;
        try {
            computerList = computerService.getComputerList(index, limit);
        } catch (ServiceException e) {
            LOG.error("{}", e);
        }
        return ResponseEntityMapper.toListResponseEntity(ComputerMapper.toComputerDtoList(computerList));
    }

    @Override
    @GetMapping("/index/{index}/limit/{limit}/field/{computerField}")
    public ResponseEntity<List<ComputerDTO>> getComputerListOrderedBy(@PathVariable long index, @PathVariable Long limit, @PathVariable DatabaseField computerField, @PathVariable boolean ascending) {
        List<Computer> computerList = null;
        try {
            computerList = computerService.getComputerListOrderedBy(index, limit, computerField, ascending);
        } catch (ServiceException e) {
            LOG.error("{}", e);
        }
        return ResponseEntityMapper.toListResponseEntity(ComputerMapper.toComputerDtoList(computerList));
    }

    @Override
    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<ComputerDTO>> getComputerListWithCompanyId(@PathVariable long companyId) {
        List<Computer> computerList = null;
        try {
            computerList = computerService.getComputerListWithCompanyId(companyId);
        } catch (ServiceException e) {
            LOG.error("{}", e);
        }
        return ResponseEntityMapper.toListResponseEntity(ComputerMapper.toComputerDtoList(computerList));
    }

    @Override
    @PutMapping
    public ResponseEntity<List<Error>> updateComputer(@RequestBody ComputerDTO computerDTO) {
        List<Error> errorList = ComputerValidator.validate(computerDTO);
        HttpStatus httpStatus = HttpStatus.OK;

        if (errorList == null) {
            try {
                Company company = null;
                if (computerDTO.getCompanyId() != null) {
                    company = companyService.getCompany(computerDTO.getCompanyId());
                }
                Computer computer = ComputerMapper.toComputer(computerDTO, company);
                if (computer.getId() != null) {
                    computerService.updateComputer(computer);
                } else {
                    computerService.persistComputer(computer);
                }
            } catch (ServiceException | ValidationException e) {
                LOG.error("{}", e);
                httpStatus = HttpStatus.NOT_MODIFIED;
            }
        } else {
            errorList.stream()
                    .filter(Objects::nonNull)
                    .forEach(System.out::println);
        }

        return new ResponseEntity<>(errorList, httpStatus);
    }

    @Override
    @PostMapping
    public ResponseEntity<Long> persistComputer(@RequestBody ComputerDTO computerDTO) {
        LOG.debug("persistComputer");
        LOG.debug("computerDTO: {}", computerDTO);
        Long id = null;
        List<Error> errorList = ComputerValidator.validate(computerDTO);

        if (errorList == null) {
            try {
                Company company = null;
                if (computerDTO.getCompanyId() != null) {
                    company = companyService.getCompany(computerDTO.getCompanyId());
                }
                Computer computer = ComputerMapper.toComputer(computerDTO, company);
                id = computerService.persistComputer(computer);
            } catch (ServiceException | ValidationException e) {
                LOG.error("{}", e);
            }
        } else {
            errorList.stream()
                    .filter(Objects::nonNull)
                    .forEach(System.out::println);
        }

        return ResponseEntityMapper.toResponseEntity(id);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteComputer(@PathVariable Long id) {
        try {
            computerService.deleteComputer(id);
        } catch (ServiceException e) {
            LOG.error("{}", e);
            return new ResponseEntity<>(Boolean.FALSE, HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/list")
    public ResponseEntity<Boolean> deleteComputers(@RequestBody List<Long> idList) {
        try {
            computerService.deleteComputers(idList);
        } catch (ServiceException e) {
            LOG.error("{}", e);
            return new ResponseEntity<>(Boolean.FALSE, HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/company/{companyId}")
    public ResponseEntity<Boolean> deleteComputersWithCompanyId(@PathVariable Long companyId) {
        try {
            computerService.deleteComputersWithCompanyId(companyId);
        } catch (ServiceException e) {
            LOG.error("{}", e);
            return new ResponseEntity<>(Boolean.FALSE, HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }
}

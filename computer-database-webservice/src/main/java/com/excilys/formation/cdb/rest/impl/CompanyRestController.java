package com.excilys.formation.cdb.rest.impl;

import com.excilys.formation.cdb.dto.model.CompanyDTO;
import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.mapper.model.CompanyMapper;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.constants.Paths;
import com.excilys.formation.cdb.rest.CompanyRest;
import com.excilys.formation.cdb.service.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(Paths.REST_COMPANY)
public class CompanyRestController implements CompanyRest {
    private static final Logger LOG = LoggerFactory.getLogger(CompanyRestController.class);

    private CompanyService companyService;

    @Autowired
    public CompanyRestController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Override
    @GetMapping("/total")
    public ResponseEntity<Long> getNumberOfCompanies() {
        LOG.debug(" {}");
        Long numberOfCompanies = null;
        try {
            numberOfCompanies = companyService.getNumberOfCompanies();
        } catch (ServiceException e) {
            LOG.error("{}", e);
        }

        return ResponseEntityMapper.toResponseEntity(numberOfCompanies);
    }

    @Override
    @GetMapping("/id/{id}")
    public ResponseEntity<CompanyDTO> getCompanyWithId(@PathVariable Long id) {
        LOG.debug(" {}");
        Company company = null;
        try {
            company = companyService.getCompany(id);
        } catch (ServiceException e) {
            LOG.error("{}", e);
        }

        return ResponseEntityMapper.toResponseEntity(CompanyMapper.toDTO(company));
    }

    @Override
    @GetMapping("/all")
    public ResponseEntity<List<CompanyDTO>> getCompanies() {
        LOG.debug(" {}");
        List<Company> companyList = null;
        try {
            companyList = companyService.getCompanies();
        } catch (ServiceException e) {
            LOG.error("{}", e);
        }

        return ResponseEntityMapper.toListResponseEntity(CompanyMapper.toCompanyDtoList(companyList));
    }

    @Override
    @GetMapping("/index/{index}/limit/{limit}")
    public ResponseEntity<List<CompanyDTO>> getCompanyList(@PathVariable Long index, @PathVariable Long limit) {
        LOG.debug(" {}");
        List<Company> companyList = null;
        try {
            companyList = companyService.getCompanies(index, limit);
        } catch (ServiceException e) {
            LOG.error("{}", e);
        }

        return ResponseEntityMapper.toListResponseEntity(CompanyMapper.toCompanyDtoList(companyList));
    }

    @Override
    @PostMapping
    public ResponseEntity<Long> persistCompany(@RequestBody CompanyDTO companyDTO) {
        LOG.debug("persistCompany");
        LOG.debug("companyDTO: {}", companyDTO);
        Long id = null;
        Company company = CompanyMapper.toCompany(companyDTO);

        if (company != null) {
            try {
                id = companyService.persistCompany(company);
            } catch (ServiceException e) {
                LOG.error("{}", e);
            }
        } else {
            LOG.error("Company already had an id!");
        }

        return ResponseEntityMapper.toResponseEntity(id);
    }

    @Override
    @PutMapping
    public ResponseEntity<Boolean> updateCompany(@RequestBody CompanyDTO companyDTO) {
        LOG.debug("updateCompany");
        LOG.debug("companyDTO: {}", companyDTO);
        Company company = CompanyMapper.toCompany(companyDTO);
        Boolean success = null;

        if (company != null) {
            try {
                companyService.updateCompany(company);
                success = Boolean.TRUE;
            } catch (ServiceException e) {
                LOG.error("{}", e);
            }
        } else {
            LOG.error("Could not update the company!");
        }

        return ResponseEntityMapper.toResponseEntity(success);
    }

    @Override
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Boolean> deleteCompany(@PathVariable Long id) {
        try {
            companyService.deleteCompany(id);
        } catch (ServiceException e) {
            LOG.error("{}", e);
            return new ResponseEntity<>(Boolean.FALSE, HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }
}

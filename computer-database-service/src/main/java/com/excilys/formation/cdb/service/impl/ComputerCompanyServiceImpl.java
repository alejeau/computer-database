package com.excilys.formation.cdb.service.impl;

import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.service.CompanyService;
import com.excilys.formation.cdb.service.ComputerCompanyService;
import com.excilys.formation.cdb.service.ComputerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@Service
@EnableTransactionManagement
public class ComputerCompanyServiceImpl implements ComputerCompanyService {

    private CompanyService companyService;
    private ComputerService computerService;

    @Autowired
    public ComputerCompanyServiceImpl(CompanyService companyService, ComputerService computerService) {
        this.companyService = companyService;
        this.computerService = computerService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCompanyWithIdAndAssociatedComputers(Long id) throws ServiceException {
        computerService.deleteComputersWithCompanyId(id);
        companyService.deleteCompany(id);
    }
}

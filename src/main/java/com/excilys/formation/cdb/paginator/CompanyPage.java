package com.excilys.formation.cdb.paginator;

import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.paginator.core.LimitValue;
import com.excilys.formation.cdb.paginator.core.Page;
import com.excilys.formation.cdb.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompanyPage extends Page<Company> {
    protected CompanyService companyService;

    public CompanyPage() {
        super();
    }

    @Autowired
    public CompanyPage(CompanyService companyService) {
        super();
        this.companyService = companyService;
    }

    public CompanyPage(LimitValue limit) {
        super(limit);
    }

    @Override
    public Long currentLastPageNumber() throws ServiceException {
        Long numberOfCompany = companyService.getNumberOfCompanies();
        return numberOfCompany / this.limit.getValue();
    }

    @Override
    protected void refresh(long offset) throws ServiceException {
        this.list = companyService.getCompanies(offset, this.limit.getValue());
    }
}

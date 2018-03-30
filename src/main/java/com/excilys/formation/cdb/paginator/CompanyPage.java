package com.excilys.formation.cdb.paginator;

import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.paginator.core.LimitValue;
import com.excilys.formation.cdb.paginator.core.Page;
import com.excilys.formation.cdb.service.CompanyService;
import com.excilys.formation.cdb.service.impl.CompanyServiceImpl;

public class CompanyPage extends Page<Company> {
    protected static CompanyService companyService = CompanyServiceImpl.INSTANCE;

    public CompanyPage() {
        super();
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

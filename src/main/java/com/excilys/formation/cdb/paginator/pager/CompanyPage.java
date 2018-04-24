package com.excilys.formation.cdb.paginator.pager;

import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.paginator.core.LimitValue;
import com.excilys.formation.cdb.paginator.core.Page;
import com.excilys.formation.cdb.service.CompanyService;

public class CompanyPage extends Page<Company> {
    protected CompanyService companyService;

    CompanyPage() {
        super();
    }

    CompanyPage(LimitValue limit) {
        super(limit);
    }

    @Override
    public Long currentLastPageNumber() throws ServiceException {
        Long numberOfCompany = companyService.getNumberOfCompanies();
        Long lastPageNumber = numberOfCompany / this.limit.getValue();
        return (numberOfCompany % 10 == 0) && (numberOfCompany != 0L) ? lastPageNumber - 1L : lastPageNumber;
    }

    @Override
    protected void refresh(long offset) throws ServiceException {
        this.list = companyService.getCompanies(offset, this.limit.getValue());
    }

    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }
}

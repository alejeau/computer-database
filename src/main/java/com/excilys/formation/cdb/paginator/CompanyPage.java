package com.excilys.formation.cdb.paginator;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.paginator.core.LimitValue;
import com.excilys.formation.cdb.paginator.core.Page;
import com.excilys.formation.cdb.service.CompanyService;

public class CompanyPage extends Page<Company> {

    public CompanyPage() {
        super();
    }

    public CompanyPage(LimitValue limit) {
        super();
    }

    @Override
    public Long currentLastPageNumber() {
        Long numberOfCompany = CompanyService.INSTANCE.getNumberOfCompanies();
        return numberOfCompany / this.limit.getValue();
    }

    @Override
    protected void refresh(long offset) {
        this.page = CompanyService.INSTANCE.getCompanies(offset, this.limit.getValue());
    }
}

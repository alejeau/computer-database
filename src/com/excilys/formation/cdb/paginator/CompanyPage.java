package com.excilys.formation.cdb.paginator;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.paginator.core.LIMIT_VALUE;
import com.excilys.formation.cdb.paginator.core.Page;
import com.excilys.formation.cdb.service.CompanyService;

import java.util.List;

public class CompanyPage extends Page<Company> {

    public CompanyPage() {
        super();
    }

    public CompanyPage(LIMIT_VALUE limit) {
        super();
    }

    @Override
    public Long currentLastPageNumber() {
        Long numberOfCompany = CompanyService.INSTANCE.getNumberOfCompanies();
        return numberOfCompany / this.limit.getValue();
    }

    @Override
    protected void refresh(Integer offset){
        this.page = CompanyService.INSTANCE.getCompanies(offset, this.limit.getValue());
    }
}

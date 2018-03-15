package com.excilys.formation.cdb.paginator;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.paginator.core.LIMIT_VALUE;
import com.excilys.formation.cdb.service.CompanyService;

import java.util.List;

public class CompanySearchPage extends CompanyPage {

    protected String search = "";

    public CompanySearchPage(String search) {
        super();
        this.search = search;
    }

    public CompanySearchPage(String search, LIMIT_VALUE limit) {
        super();
        this.search = search;
    }

    @Override
    public Long currentLastPageNumber() {
        Long numberOfComputer = CompanyService.INSTANCE.getNumberOfCompaniesWithName(this.search);
        return numberOfComputer / this.limit.getValue();
    }

    @Override
    protected void refresh(Integer offset){
        this.page = CompanyService.INSTANCE.getCompany(search, offset, this.limit.getValue());
    }
}

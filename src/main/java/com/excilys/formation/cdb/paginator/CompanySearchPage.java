package com.excilys.formation.cdb.paginator;

import com.excilys.formation.cdb.paginator.core.LimitValue;
import com.excilys.formation.cdb.service.CompanyService;

public class CompanySearchPage extends CompanyPage {

    protected String search;

    public CompanySearchPage(String search) {
        super();
        this.search = search;
    }

    public CompanySearchPage(String search, LimitValue limit) {
        super();
        this.search = search;
    }

    @Override
    public Long currentLastPageNumber() {
        Long numberOfComputer = CompanyService.INSTANCE.getNumberOfCompaniesWithName(this.search);
        return numberOfComputer / this.limit.getValue();
    }

    @Override
    protected void refresh(Integer offset) {
        this.page = CompanyService.INSTANCE.getCompany(search, offset, this.limit.getValue());
    }
}

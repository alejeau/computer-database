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
        super(limit);
        this.search = search;
    }

    @Override
    public Long currentLastPageNumber() {
        Long numberOfComputer = CompanyService.INSTANCE.getNumberOfCompaniesWithName(this.search);
        return numberOfComputer / this.limit.getValue();
    }

    @Override
    protected void refresh(long offset) {
        this.page = CompanyService.INSTANCE.getCompany(search, offset, this.limit.getValue());
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}

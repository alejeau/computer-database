package com.excilys.formation.cdb.paginator.pager;

import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.paginator.core.LimitValue;

public class CompanySearchPage extends CompanyPage {
    protected String search;

    CompanySearchPage() {
        super();
    }

    CompanySearchPage(String search) {
        super();
        this.search = search;
    }

    CompanySearchPage(String search, LimitValue limit) {
        super(limit);
        this.search = search;
    }

    @Override
    public Long currentLastPageNumber() throws ServiceException {
        Long numberOfComputer = companyService.getNumberOfCompaniesWithName(this.search);
        Long lastPageNumber = numberOfComputer / this.limit.getValue();
        return numberOfComputer % 10 == 0 ? lastPageNumber - 1L : lastPageNumber;
    }

    @Override
    protected void refresh(long offset) throws ServiceException {
        this.list = companyService.getCompany(search, offset, this.limit.getValue());
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}

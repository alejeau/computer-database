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
        Long numberOfCompany = companyService.getNumberOfCompaniesWithName(this.search);
        Long lastPageNumber = numberOfCompany / this.limit.getValue();
        return (numberOfCompany % 10 == 0) && (numberOfCompany != 0L) ? lastPageNumber - 1L : lastPageNumber;
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

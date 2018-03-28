package com.excilys.formation.cdb.paginator;

import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.paginator.core.LimitValue;
import com.excilys.formation.cdb.service.impl.CompanyServiceImpl;

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
    public Long currentLastPageNumber() throws ServiceException {
        Long numberOfComputer = CompanyServiceImpl.INSTANCE.getNumberOfCompaniesWithName(this.search);
        return numberOfComputer / this.limit.getValue();
    }

    @Override
    protected void refresh(long offset)throws ServiceException {
            this.page = CompanyServiceImpl.INSTANCE.getCompany(search, offset, this.limit.getValue());
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}

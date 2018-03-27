package com.excilys.formation.cdb.paginator;

import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.paginator.core.LimitValue;
import com.excilys.formation.cdb.service.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        Long numberOfComputer = CompanyService.INSTANCE.getNumberOfCompaniesWithName(this.search);
        return numberOfComputer / this.limit.getValue();
    }

    @Override
    protected void refresh(long offset)throws ServiceException {
            this.page = CompanyService.INSTANCE.getCompany(search, offset, this.limit.getValue());
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}

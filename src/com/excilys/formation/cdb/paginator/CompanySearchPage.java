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
    public List<Company> goToPage(Long pageNumber) {
        this.checkValidPageNumber(pageNumber, currentLastPageNumber());

        Integer start = this.pageNumber * this.limit.getValue();
        this.page = CompanyService.INSTANCE.getCompany(search, start, this.limit.getValue());
        return this.page;
    }

    @Override
    public List<Company> previous() {
        this.checkPreviousPageNumber();

        Integer start = this.pageNumber * this.limit.getValue();
        this.page = CompanyService.INSTANCE.getCompany(search, start, this.limit.getValue());
        return this.page;
    }

    @Override
    public List<Company> next() {
        this.checkNextPageNumber(this.currentLastPageNumber());

        Integer start = this.pageNumber * this.limit.getValue();
        this.page = CompanyService.INSTANCE.getCompany(search, start, this.limit.getValue());
        return this.page;
    }

    @Override
    public List<Company> first() {
        this.page = CompanyService.INSTANCE.getCompany(search, FIRST_PAGE, this.limit.getValue());
        return this.page;
    }

    @Override
    public List<Company> last() {
        this.pageNumber = currentLastPageNumber().intValue();
        Integer start = this.pageNumber * this.limit.getValue();
        this.page = CompanyService.INSTANCE.getCompany(search, start, this.limit.getValue());
        return this.page;
    }

    @Override
    public Long currentLastPageNumber() {
        Long numberOfComputer = CompanyService.INSTANCE.getNumberOfCompaniesWithName(this.search);
        return numberOfComputer / this.limit.getValue();
    }
}

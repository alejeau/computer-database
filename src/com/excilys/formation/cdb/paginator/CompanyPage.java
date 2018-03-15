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
    public List<Company> goToPage(Long pageNumber) {
        this.checkValidPageNumber(pageNumber, currentLastPageNumber());

        Integer start = this.pageNumber * this.limit.getValue();
        this.page = CompanyService.INSTANCE.getCompanies(start, this.limit.getValue());
        return this.page;
    }

    @Override
    public List<Company> previous() {
        this.checkPreviousPageNumber();

        Integer start = this.pageNumber * this.limit.getValue();
        this.page = CompanyService.INSTANCE.getCompanies(start, this.limit.getValue());
        return this.page;
    }

    @Override
    public List<Company> next() {
        this.checkNextPageNumber(this.currentLastPageNumber());

        Integer start = this.pageNumber * this.limit.getValue();
        this.page = CompanyService.INSTANCE.getCompanies(start, this.limit.getValue());
        return this.page;
    }

    @Override
    public List<Company> first() {
        this.page = CompanyService.INSTANCE.getCompanies(FIRST_PAGE, this.limit.getValue());
        return this.page;
    }

    @Override
    public List<Company> last() {
        this.pageNumber = currentLastPageNumber().intValue();
        Integer start = this.pageNumber * this.limit.getValue();
        this.page = CompanyService.INSTANCE.getCompanies(start, this.limit.getValue());
        return this.page;
    }

    @Override
    public Long currentLastPageNumber() {
        Long numberOfCompany = CompanyService.INSTANCE.getNumberOfCompanies();
        return numberOfCompany / this.limit.getValue();
    }
}

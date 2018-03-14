package com.excilys.formation.cdb.paginator;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.paginator.core.OFFSET_VALUE;
import com.excilys.formation.cdb.paginator.core.Page;
import com.excilys.formation.cdb.service.CompanyService;

import java.util.List;

public class CompanyPage extends Page<Company> {

    public CompanyPage() {
        super();
    }

    public CompanyPage(Integer pageNumber, OFFSET_VALUE offset) {
        super();
    }

    protected List<Company> previous() {
        this.checkPreviousPageNumber();

        Integer start = this.pageNumber * this.offset.getValue();
        this.page = CompanyService.INSTANCE.getCompanies(start, this.offset.getValue());
        return this.page;
    }

    protected List<Company> next() {
        this.checkNextPageNumber(this.currentLastPageNumber());

        Integer start = this.pageNumber * this.offset.getValue();
        this.page = CompanyService.INSTANCE.getCompanies(start, this.offset.getValue());
        return this.page;
    }

    protected List<Company> first() {
        this.page = CompanyService.INSTANCE.getCompanies(PAGE_START, this.offset.getValue());
        return this.page;
    }

    protected List<Company> last() {

        Integer start = this.pageNumber * this.offset.getValue();
        this.page = CompanyService.INSTANCE.getCompanies(start, this.offset.getValue());
        return this.page;
    }

    protected Long currentLastPageNumber() {
        Long numberOfCompany = CompanyService.INSTANCE.getNumberOfCompanies();
        return numberOfCompany / this.offset.getValue() + 1;
    }
}

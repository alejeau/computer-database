package com.excilys.formation.cdb.paginator;

import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.paginator.core.LIMIT_VALUE;
import com.excilys.formation.cdb.service.ComputerService;

import java.util.List;

public class ComputerSearchPage extends ComputerPage {

    protected String search = "";

    public ComputerSearchPage(String search) {
        super();
        this.search = search;
    }

    public ComputerSearchPage(String search, LIMIT_VALUE limit) {
        super();
        this.search = search;
    }

    @Override
    public List<Computer> goToPage(Long pageNumber) {
        this.checkValidPageNumber(pageNumber, currentLastPageNumber());

        Integer start = this.pageNumber * this.limit.getValue();
        this.page = ComputerService.INSTANCE.getComputer(search, start, this.limit.getValue());
        return this.page;
    }

    @Override
    public List<Computer> previous() {
        this.checkPreviousPageNumber();

        Integer start = this.pageNumber * this.limit.getValue();
        this.page = ComputerService.INSTANCE.getComputer(search, start, this.limit.getValue());
        return this.page;
    }

    @Override
    public List<Computer> next() {
        this.checkNextPageNumber(this.currentLastPageNumber());

        Integer start = this.pageNumber * this.limit.getValue();
        System.out.println("this.pageNumber: " + this.pageNumber + ", this.limit.getValue(): " + this.limit.getValue() + ", start: " + start);
        this.page = ComputerService.INSTANCE.getComputer(search, start, this.limit.getValue());
        return this.page;
    }

    @Override
    public List<Computer> first() {
        this.page = ComputerService.INSTANCE.getComputer(search, FIRST_PAGE, this.limit.getValue());
        return this.page;
    }

    @Override
    public List<Computer> last() {
        this.pageNumber = currentLastPageNumber().intValue();
        Integer start = this.pageNumber * this.limit.getValue();
        this.page = ComputerService.INSTANCE.getComputer(search, start, this.limit.getValue());
        return this.page;
    }

    @Override
    public Long currentLastPageNumber() {
        Long numberOfComputer = ComputerService.INSTANCE.getNumberOfCompaniesWithName(this.search);
        return numberOfComputer / this.limit.getValue();
    }
}

package com.excilys.formation.cdb.paginator;

import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.paginator.core.OFFSET_VALUE;
import com.excilys.formation.cdb.service.ComputerService;

import java.util.List;

public class ComputerSearchPage extends ComputerPage {

    protected String search = "";

    public ComputerSearchPage(String search) {
        super();
        this.search = search;
    }

    public ComputerSearchPage(String search, OFFSET_VALUE offset) {
        super();
        this.search = search;
    }

    public List<Computer> goToPage(Long pageNumber) {
        this.checkValidPageNumber(pageNumber, currentLastPageNumber());

        Integer start = this.pageNumber * this.offset.getValue();
        this.page = ComputerService.INSTANCE.getComputer(search, start, this.offset.getValue());
        return this.page;
    }

    public List<Computer> previous() {
        this.checkPreviousPageNumber();

        Integer start = this.pageNumber * this.offset.getValue();
        this.page = ComputerService.INSTANCE.getComputer(search, start, this.offset.getValue());
        return this.page;
    }

    public List<Computer> next() {
        this.checkNextPageNumber(this.currentLastPageNumber());

        Integer start = this.pageNumber * this.offset.getValue();
        this.page = ComputerService.INSTANCE.getComputer(search, start, this.offset.getValue());
        return this.page;
    }

    public List<Computer> first() {
        this.page = ComputerService.INSTANCE.getComputer(search, FIRST_PAGE, this.offset.getValue());
        return this.page;
    }

    public List<Computer> last() {

        Integer start = this.pageNumber * this.offset.getValue();
        this.page = ComputerService.INSTANCE.getComputer(search, start, this.offset.getValue());
        return this.page;
    }

    public Long currentLastPageNumber() {
        Long numberOfComputer = ComputerService.INSTANCE.getNumberOfCompaniesWithName(this.search);
        return numberOfComputer / this.offset.getValue() + 1;
    }
}

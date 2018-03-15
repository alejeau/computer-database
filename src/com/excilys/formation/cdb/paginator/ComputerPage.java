package com.excilys.formation.cdb.paginator;

import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.paginator.core.OFFSET_VALUE;
import com.excilys.formation.cdb.paginator.core.Page;
import com.excilys.formation.cdb.service.ComputerService;

import java.util.List;

public class ComputerPage extends Page<Computer> {

    public ComputerPage() {
        super();
    }

    public ComputerPage(OFFSET_VALUE offset) {
        super();
    }

    @Override
    public List<Computer> goToPage(Long pageNumber) {
        this.checkValidPageNumber(pageNumber, currentLastPageNumber());

        Integer start = this.pageNumber * this.offset.getValue();
        this.page = ComputerService.INSTANCE.getComputers(start, this.offset.getValue());
        return this.page;
    }

    @Override
    public List<Computer> previous() {
        this.checkPreviousPageNumber();

        Integer start = this.pageNumber * this.offset.getValue();
        this.page = ComputerService.INSTANCE.getComputers(start, this.offset.getValue());
        return this.page;
    }

    @Override
    public List<Computer> next() {
        this.checkNextPageNumber(this.currentLastPageNumber());

        Integer start = this.pageNumber * this.offset.getValue();
        System.out.println("this.pageNumber: " + this.pageNumber + ", this.offset.getValue(): " + this.offset.getValue() + ", start: " + start);
        this.page = ComputerService.INSTANCE.getComputers(start, this.offset.getValue());
        return this.page;
    }

    @Override
    public List<Computer> first() {
        this.page = ComputerService.INSTANCE.getComputers(FIRST_PAGE, this.offset.getValue());

        return this.page;
    }

    @Override
    public List<Computer> last() {
        this.pageNumber = currentLastPageNumber().intValue();
        Integer start = this.pageNumber * this.offset.getValue();
        this.page = ComputerService.INSTANCE.getComputers(start, this.offset.getValue());
        return this.page;
    }

    @Override
    public Long currentLastPageNumber() {
        Long numberOfComputer = ComputerService.INSTANCE.getNumberOfComputers();
        return numberOfComputer / this.offset.getValue();
    }
}

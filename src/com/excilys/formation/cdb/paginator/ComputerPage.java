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

    public ComputerPage(Integer pageNumber, OFFSET_VALUE offset) {
        super();
    }

    protected List<Computer> previous() {
        this.checkPreviousPageNumber();

        Integer start = this.pageNumber * this.offset.getValue();
        this.page = ComputerService.INSTANCE.getComputers(start, this.offset.getValue());
        return this.page;
    }

    protected List<Computer> next() {
        this.checkNextPageNumber(this.currentLastPageNumber());

        Integer start = this.pageNumber * this.offset.getValue();
        this.page = ComputerService.INSTANCE.getComputers(start, this.offset.getValue());
        return this.page;
    }

    protected List<Computer> first() {
        this.page = ComputerService.INSTANCE.getComputers(PAGE_START, this.offset.getValue());
        return this.page;
    }

    protected List<Computer> last() {

        Integer start = this.pageNumber * this.offset.getValue();
        this.page = ComputerService.INSTANCE.getComputers(start, this.offset.getValue());
        return this.page;
    }

    protected Long currentLastPageNumber() {
        Long numberOfComputer = ComputerService.INSTANCE.getNumberOfComputers();
        return numberOfComputer / this.offset.getValue() + 1;
    }
}

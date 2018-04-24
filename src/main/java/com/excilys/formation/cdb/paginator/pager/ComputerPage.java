package com.excilys.formation.cdb.paginator.pager;

import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.paginator.core.LimitValue;
import com.excilys.formation.cdb.paginator.core.Page;
import com.excilys.formation.cdb.service.ComputerService;

public class ComputerPage extends Page<Computer> {
    protected ComputerService computerService;

    ComputerPage() {
        super();
    }

    ComputerPage(LimitValue limit) {
        super(limit);
    }

    @Override
    public Long currentLastPageNumber() throws ServiceException {
        Long numberOfComputer = computerService.getNumberOfComputers();
        Long lastPageNumber = numberOfComputer / this.limit.getValue();
        return (numberOfComputer % 10 == 0) && (numberOfComputer != 0L) ? lastPageNumber - 1L : lastPageNumber;
    }

    @Override
    protected void refresh(long offset) throws ServiceException {
        this.list = computerService.getComputers(offset, limit.getValue());
    }

    public void setComputerService(ComputerService computerService) {
        this.computerService = computerService;
    }
}

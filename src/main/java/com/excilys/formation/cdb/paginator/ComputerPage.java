package com.excilys.formation.cdb.paginator;

import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.paginator.core.LimitValue;
import com.excilys.formation.cdb.paginator.core.Page;
import com.excilys.formation.cdb.service.ComputerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ComputerPage extends Page<Computer> {
    @Autowired
    protected ComputerService computerService;

    public ComputerPage() {
        super();
    }

    public ComputerPage(LimitValue limit) {
        super(limit);
    }

    @Override
    public Long currentLastPageNumber() throws ServiceException {
        Long numberOfComputer = computerService.getNumberOfComputers();
        return numberOfComputer / this.limit.getValue();
    }

    @Override
    protected void refresh(long offset) throws ServiceException {
        this.list = computerService.getComputers(offset, limit.getValue());
    }

    public ComputerService getComputerService() {
        return computerService;
    }

    public void setComputerService(ComputerService computerService) {
        this.computerService = computerService;
    }
}

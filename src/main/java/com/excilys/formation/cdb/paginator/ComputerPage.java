package com.excilys.formation.cdb.paginator;

import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.paginator.core.LimitValue;
import com.excilys.formation.cdb.paginator.core.Page;
import com.excilys.formation.cdb.service.impl.ComputerServiceImpl;

public class ComputerPage extends Page<Computer> {

    public ComputerPage() {
        super();
    }

    public ComputerPage(LimitValue limit) {
        super(limit);
    }

    @Override
    public Long currentLastPageNumber()throws ServiceException {
        Long numberOfComputer = ComputerServiceImpl.INSTANCE.getNumberOfComputers();
        return numberOfComputer / this.limit.getValue();
    }

    @Override
    protected void refresh(long offset)throws ServiceException {
        this.page = ComputerServiceImpl.INSTANCE.getComputers(offset, limit.getValue());
    }
}

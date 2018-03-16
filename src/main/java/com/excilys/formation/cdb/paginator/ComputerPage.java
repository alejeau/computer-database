package com.excilys.formation.cdb.paginator;

import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.paginator.core.LimitValue;
import com.excilys.formation.cdb.paginator.core.Page;
import com.excilys.formation.cdb.service.ComputerService;

public class ComputerPage extends Page<Computer> {

    public ComputerPage() {
        super();
    }

    public ComputerPage(LimitValue limit) {
        super();
    }

    @Override
    public Long currentLastPageNumber() {
        Long numberOfComputer = ComputerService.INSTANCE.getNumberOfComputers();
        return numberOfComputer / this.limit.getValue();
    }

    @Override
    protected void refresh(Integer offset) {
        this.page = ComputerService.INSTANCE.getComputers(offset, this.limit.getValue());
    }
}

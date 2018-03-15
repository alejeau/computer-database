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
    public Long currentLastPageNumber() {
        Long numberOfComputer = ComputerService.INSTANCE.getNumberOfCompaniesWithName(this.search);
        return numberOfComputer / this.limit.getValue();
    }

    @Override
    protected void refresh(Integer offset){
        this.page = ComputerService.INSTANCE.getComputer(search, offset, this.limit.getValue());
    }
}

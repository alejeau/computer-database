package com.excilys.formation.cdb.paginator;

import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.paginator.core.LimitValue;
import com.excilys.formation.cdb.service.impl.ComputerServiceImpl;

public class ComputerSearchPage extends ComputerPage {

    protected String search;

    public ComputerSearchPage(String search) {
        super();
        this.search = search;
    }

    public ComputerSearchPage(String search, LimitValue limit) {
        super(limit);
        this.search = search;
    }

    @Override
    public Long currentLastPageNumber() throws ServiceException {
        Long numberOfComputer = ComputerServiceImpl.INSTANCE.getNumberOfComputersWithName(this.search);
        return numberOfComputer / this.limit.getValue();
    }

    @Override
    protected void refresh(long offset) throws ServiceException {
        this.page = ComputerServiceImpl.INSTANCE.getComputer(search, offset, this.limit.getValue());
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}

package com.excilys.formation.cdb.paginator.pager;

import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.paginator.core.LimitValue;

public class ComputerSearchPage extends ComputerPage {
    protected String search;

    ComputerSearchPage() {
        super();
    }

    ComputerSearchPage(String search) {
        super();
        this.search = search;
    }

    ComputerSearchPage(String search, LimitValue limit) {
        super(limit);
        this.search = search;
    }

    @Override
    public Long currentLastPageNumber() throws ServiceException {
        Long numberOfComputer = computerService.getNumberOfComputersWithName(this.search);
        Long lastPageNumber = numberOfComputer / this.limit.getValue();
        return (numberOfComputer % 10 == 0) && (numberOfComputer != 0L) ? lastPageNumber - 1L : lastPageNumber;
    }

    @Override
    protected void refresh(long offset) throws ServiceException {
        this.list = computerService.getComputer(search, offset, this.limit.getValue());
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}

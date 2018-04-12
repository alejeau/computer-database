package com.excilys.formation.cdb.paginator;

import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.paginator.core.LimitValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComputerSearchPage extends ComputerPage {
    private static final Logger LOG = LoggerFactory.getLogger(ComputerSearchPage.class);
    protected String search;

    public ComputerSearchPage() {
        super();
    }

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
        Long numberOfComputer = computerService.getNumberOfComputersWithName(this.search);
        Long lastPageNumber = numberOfComputer / this.limit.getValue();
        return numberOfComputer % 10 == 0 ? lastPageNumber - 1L : lastPageNumber;
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

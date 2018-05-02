package com.excilys.formation.cdb.service.paginator.pager;

import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.model.constants.ComputerField;
import com.excilys.formation.cdb.model.constants.LimitValue;
import com.excilys.formation.cdb.persistence.DatabaseField;

public class ComputerSortedSearchPage extends ComputerSearchPage {
    private ComputerField orderBy = ComputerField.COMPUTER_NAME;
    private boolean ascending = true;

    ComputerSortedSearchPage() {
        super();
    }

    ComputerSortedSearchPage(String search) {
        super(search);
    }

    ComputerSortedSearchPage(String search, LimitValue limit) {
        super(search, limit);
    }

    ComputerSortedSearchPage(String search, LimitValue limit, ComputerField orderBy, boolean ascending) {
        super(search, limit);
        this.orderBy = orderBy;
        this.ascending = ascending;
    }

    @Override
    protected void refresh(long offset) throws ServiceException {
        this.list = computerService
                .getComputerOrderedBy(search, offset, limit.getValue(), DatabaseField.toDatabaseField(orderBy), this.ascending);
    }

    public ComputerField getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(ComputerField orderBy) {
        this.orderBy = orderBy;
    }

    public boolean isAscending() {
        return ascending;
    }

    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }
}

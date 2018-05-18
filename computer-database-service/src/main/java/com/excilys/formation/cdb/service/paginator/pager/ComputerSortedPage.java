package com.excilys.formation.cdb.service.paginator.pager;

import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.model.constants.ComputerField;
import com.excilys.formation.cdb.model.constants.LimitValue;
import com.excilys.formation.cdb.persistence.DatabaseField;

public class ComputerSortedPage extends ComputerPage {
    private ComputerField orderBy = ComputerField.COMPUTER_NAME;
    private boolean ascending = true;

    ComputerSortedPage() {
        super();
    }

    ComputerSortedPage(LimitValue limit) {
        super(limit);
    }

    ComputerSortedPage(LimitValue limit, ComputerField orderBy, boolean ascending) {
        super(limit);
        this.orderBy = orderBy;
        this.ascending = ascending;
    }

    @Override
    protected void refresh(long offset) throws ServiceException {
        this.list = computerService
                .getComputerListOrderedBy(offset, limit.getValue(), DatabaseField.toDatabaseField(orderBy), this.ascending);
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

package com.excilys.formation.cdb.paginator.pager;

import com.excilys.formation.cdb.controllers.constants.ComputerField;
import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.mapper.DatabaseFieldsMapper;
import com.excilys.formation.cdb.paginator.core.LimitValue;

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
                .getComputersOrderedBy(offset, limit.getValue(), DatabaseFieldsMapper.toDatabaseField(orderBy), this.ascending);
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

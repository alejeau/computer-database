package com.excilys.formation.cdb.paginator;

import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.mapper.DatabaseFieldsMapper;
import com.excilys.formation.cdb.paginator.core.LimitValue;
import com.excilys.formation.cdb.service.ComputerService;
import com.excilys.formation.cdb.service.impl.ComputerServiceImpl;
import com.excilys.formation.cdb.servlets.constants.ComputerField;

public class ComputerSortedPage extends ComputerPage {
    private ComputerService computerService = ComputerServiceImpl.INSTANCE;
    private ComputerField orderBy = ComputerField.COMPUTER_NAME;
    private boolean ascending = true;

    public ComputerSortedPage() {
        super();
    }

    public ComputerSortedPage(LimitValue limit) {
        super(limit);
    }

    public ComputerSortedPage(LimitValue limit, ComputerField orderBy, boolean ascending) {
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

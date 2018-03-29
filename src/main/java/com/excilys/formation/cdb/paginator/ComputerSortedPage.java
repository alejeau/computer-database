package com.excilys.formation.cdb.paginator;

import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.mapper.DatabaseFieldsMapper;
import com.excilys.formation.cdb.paginator.core.LimitValue;
import com.excilys.formation.cdb.service.ComputerService;
import com.excilys.formation.cdb.service.impl.ComputerServiceImpl;
import com.excilys.formation.cdb.servlets.constants.ComputerField;

public class ComputerSortedPage extends ComputerPage {
    private ComputerField orderBy;
    private boolean ascending;
    private ComputerService computerService = ComputerServiceImpl.INSTANCE;

    public ComputerSortedPage() {
        super();
        this.orderBy = ComputerField.COMPUTER_NAME;
        this.ascending = true;
    }

    public ComputerSortedPage(LimitValue limit) {
        super(limit);
        this.orderBy = ComputerField.COMPUTER_NAME;
        this.ascending = true;
    }

    public ComputerSortedPage(LimitValue limit, ComputerField computerField, boolean ascending) {
        super(limit);
        this.orderBy = computerField;
        this.ascending = ascending;
    }

    @Override
    public Long currentLastPageNumber() throws ServiceException {
        return super.currentLastPageNumber();
    }

    @Override
    protected void refresh(long offset) throws ServiceException {
        this.page = computerService
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

package com.excilys.formation.cdb.paginator;

import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.mapper.DatabaseFieldsMapper;
import com.excilys.formation.cdb.paginator.core.LimitValue;
import com.excilys.formation.cdb.servlets.constants.ComputerField;
import org.springframework.stereotype.Component;

public class ComputerSortedSearchPage extends ComputerSearchPage {
    private ComputerField orderBy = ComputerField.COMPUTER_NAME;
    private boolean ascending = true;

    public ComputerSortedSearchPage() {
        super();
    }

    public ComputerSortedSearchPage(String search) {
        super(search);
    }

    public ComputerSortedSearchPage(String search, LimitValue limit) {
        super(search, limit);
    }


    public ComputerSortedSearchPage(String search, LimitValue limit, ComputerField orderBy, boolean ascending) {
        super(search, limit);
        this.orderBy = orderBy;
        this.ascending = ascending;
    }

    @Override
    protected void refresh(long offset) throws ServiceException {
        this.list = computerService
                .getComputerOrderedBy(search, offset, limit.getValue(), DatabaseFieldsMapper.toDatabaseField(orderBy), this.ascending);
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

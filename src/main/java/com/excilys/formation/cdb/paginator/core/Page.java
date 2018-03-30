package com.excilys.formation.cdb.paginator.core;

import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.model.Model;

import java.util.List;

/**
 * Page class to enable the pagination.<br>
 * The index starts at 0.
 *
 * @param <T> A model extending the Model class.
 */
public abstract class Page<T extends Model> {
    public static final Long FIRST_PAGE = 0L;

    private Long pageNumber;

    protected List<T> list = null;
    protected LimitValue limit;

    protected Page() {
        this.pageNumber = FIRST_PAGE;
        this.limit = LimitValue.TEN;
    }

    protected Page(LimitValue limit) {
        this.pageNumber = FIRST_PAGE;
        this.limit = limit;
    }

    protected abstract Long currentLastPageNumber() throws ServiceException;

    protected abstract void refresh(long offset) throws ServiceException;

    public List<T> getList() {
        return list;
    }

    public Long getPageNumber() {
        return pageNumber;
    }

    public LimitValue getLimit() {
        return limit;
    }

    public void setLimit(LimitValue limit) {
        this.limit = limit;
    }

    public List<T> goToPage(Long pageNumber) throws ServiceException {
        this.checkValidPageNumber(pageNumber, currentLastPageNumber());
        long start = this.pageNumber * this.limit.getValue();
        this.refresh(start);
        return this.list;
    }

    public List<T> previous() throws ServiceException {
        this.checkPreviousPageNumber();
        long offset = this.pageNumber * this.limit.getValue();
        this.refresh(offset);
        return this.list;
    }

    public List<T> next() throws ServiceException {
        this.checkNextPageNumber(this.currentLastPageNumber());
        long offset = this.pageNumber * this.limit.getValue();
        this.refresh(offset);
        return this.list;
    }

    public List<T> first() throws ServiceException {
        this.pageNumber = FIRST_PAGE;
        this.refresh(FIRST_PAGE);
        return this.list;
    }

    public List<T> last() throws ServiceException {
        this.pageNumber = currentLastPageNumber();
        long offset = this.pageNumber * this.limit.getValue();
        this.refresh(offset);
        return this.list;
    }

    private void checkValidPageNumber(Long requestedPage, Long lastPageNumber) {
        if (requestedPage >= FIRST_PAGE && requestedPage <= lastPageNumber) {
            this.pageNumber = requestedPage;
        } else {
            this.pageNumber = requestedPage < FIRST_PAGE ? FIRST_PAGE : lastPageNumber.intValue();
        }
    }

    private void checkPreviousPageNumber() {
        if (this.pageNumber - 1 >= FIRST_PAGE) {
            this.pageNumber--;
        }
    }

    private void checkNextPageNumber(Long currentLastPageNumber) {
        if (this.pageNumber + 1 <= currentLastPageNumber) {
            this.pageNumber++;
        }
    }
}

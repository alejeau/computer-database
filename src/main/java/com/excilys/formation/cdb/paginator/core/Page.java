package com.excilys.formation.cdb.paginator.core;

import com.excilys.formation.cdb.model.Model;

import java.util.List;

/**
 *  Page class to enable the pagination.<br>
 *  The index starts at 0.
 * @param <T> A model extending the Model class.
 */
public abstract class Page<T extends Model> {
    protected List<T> page = null;
    protected Integer pageNumber;
    protected LIMIT_VALUE limit;
    protected static final Integer FIRST_PAGE = 0;

    protected Page() {
        this.pageNumber = FIRST_PAGE;
        this.limit = LIMIT_VALUE.TEN;
    }

    protected Page(LIMIT_VALUE limit) {
        this.pageNumber = FIRST_PAGE;
        this.limit = limit;
    }

    protected abstract Long currentLastPageNumber();
    protected abstract void refresh(Integer offset);

    public List<T> getPage() {
        return page;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.checkPageNumber(pageNumber);
    }

    public LIMIT_VALUE getLimit() {
        return limit;
    }

    public void setLimit(LIMIT_VALUE limit) {
        this.limit = limit;
    }

    public List<T> goToPage(Long pageNumber) {
        this.checkValidPageNumber(pageNumber, currentLastPageNumber());
        Integer start = this.pageNumber * this.limit.getValue();
        this.refresh(start);
        return this.page;
    }

    public List<T> previous() {
        this.checkPreviousPageNumber();
        Integer offset = this.pageNumber * this.limit.getValue();
        this.refresh(offset);
        return this.page;
    }

    public List<T> next() {
        this.checkNextPageNumber(this.currentLastPageNumber());
        Integer offset = this.pageNumber * this.limit.getValue();
        this.refresh(offset);
        return this.page;
    }

    public List<T> first() {
        this.refresh(FIRST_PAGE);
        return this.page;
    }

    public List<T> last() {
        this.pageNumber = currentLastPageNumber().intValue();
        Integer offset = this.pageNumber * this.limit.getValue();
        this.refresh(offset);
        return this.page;
    }

    protected void checkPageNumber(Integer pageNumber) {
        if (pageNumber >= FIRST_PAGE)
            this.pageNumber = pageNumber;
        else
            this.pageNumber = FIRST_PAGE;
    }

    protected void checkValidPageNumber(Long requestedPage, Long currentLastPageNumber) {
        if (requestedPage >= FIRST_PAGE && requestedPage <= currentLastPageNumber)
            this.pageNumber = requestedPage.intValue();
        else
            this.pageNumber = requestedPage < FIRST_PAGE ? FIRST_PAGE : currentLastPageNumber.intValue();
    }

    protected void checkPreviousPageNumber() {
        if (this.pageNumber-1 >= FIRST_PAGE)
            this.pageNumber--;
    }

    protected void checkNextPageNumber(Long currentLastPageNumber) {
        if (this.pageNumber+1 <= currentLastPageNumber)
            this.pageNumber++;
    }
}

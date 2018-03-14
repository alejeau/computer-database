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
    protected OFFSET_VALUE offset;
    protected static final Integer FIRST_PAGE = 0;

    protected Page() {
        this.pageNumber = FIRST_PAGE;
        this.offset = OFFSET_VALUE.TEN;
    }

    protected Page(OFFSET_VALUE offset) {
        this.pageNumber = FIRST_PAGE;
        this.offset = offset;
    }

    protected abstract List<T> goToPage(Long pageNumber);
    protected abstract List<T> previous();
    protected abstract List<T> next();
    protected abstract List<T> first();
    protected abstract List<T> last();
    protected abstract Long currentLastPageNumber();

    public List<T> getPage() {
        return page;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.checkPageNumber(pageNumber);
    }

    public OFFSET_VALUE getOffset() {
        return offset;
    }

    public void setOffset(OFFSET_VALUE offset) {
        this.offset = offset;
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
        else
            this.pageNumber = FIRST_PAGE;
    }

    protected void checkNextPageNumber(Long currentLastPageNumber) {
        if (this.pageNumber+1 <= currentLastPageNumber)
            this.pageNumber++;
        else
            this.pageNumber = currentLastPageNumber.intValue();
    }
}

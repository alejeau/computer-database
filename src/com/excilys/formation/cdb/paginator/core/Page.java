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
    protected static final Integer PAGE_START = 0;

    protected Page() {
        this.pageNumber = PAGE_START;
        this.offset = OFFSET_VALUE.TEN;
    }

    protected Page(Integer pageNumber, OFFSET_VALUE offset) {
        this.checkPageNumber(pageNumber);
        this.offset = offset;
    }

    protected abstract List<T> previous();
    protected abstract List<T> next();
    protected abstract List<T> first();
    protected abstract List<T> last();
    protected abstract Long currentLastPageNumber();

    protected List<T> getPage() {
        return page;
    }

    protected Integer getPageNumber() {
        return pageNumber;
    }

    protected void setPageNumber(Integer pageNumber) {
        this.checkPageNumber(pageNumber);
    }

    protected OFFSET_VALUE getOffset() {
        return offset;
    }

    protected void setOffset(OFFSET_VALUE offset) {
        this.offset = offset;
    }

    protected void checkPageNumber(Integer pageNumber) {
        if (pageNumber >= PAGE_START)
            this.pageNumber = pageNumber;
        else
            this.pageNumber = PAGE_START;
    }

    protected void checkPreviousPageNumber() {
        if (this.pageNumber-1 >= PAGE_START)
            this.pageNumber--;
        else
            this.pageNumber = PAGE_START;
    }

    protected void checkNextPageNumber(Long currentLastPageNumber) {
        if (this.pageNumber+1 <= currentLastPageNumber)
            this.pageNumber++;
        else
            this.pageNumber = currentLastPageNumber.intValue();
    }
}

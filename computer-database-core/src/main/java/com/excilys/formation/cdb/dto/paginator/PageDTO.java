package com.excilys.formation.cdb.dto.paginator;

import com.excilys.formation.cdb.dto.ModelDTO;

import java.util.List;

public class PageDTO<T extends ModelDTO> {
    protected List<T> content;
    protected long currentPageNumber;
    protected long maxPageNumber;
    protected long objectsPerPage;
    protected long numberOfEntries;

    public PageDTO() {
    }

    public PageDTO(List<T> content, long currentPageNumber, long maxPageNumber, long objectsPerPage, long numberOfEntries) {
        this.content = content;
        this.currentPageNumber = currentPageNumber;
        this.maxPageNumber = maxPageNumber;
        this.objectsPerPage = objectsPerPage;
        this.numberOfEntries = numberOfEntries;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public long getCurrentPageNumber() {
        return currentPageNumber;
    }

    public void setCurrentPageNumber(long currentPageNumber) {
        this.currentPageNumber = currentPageNumber;
    }

    public long getMaxPageNumber() {
        return maxPageNumber;
    }

    public void setMaxPageNumber(long maxPageNumber) {
        this.maxPageNumber = maxPageNumber;
    }

    public long getObjectsPerPage() {
        return objectsPerPage;
    }

    public void setObjectsPerPage(long objectsPerPage) {
        this.objectsPerPage = objectsPerPage;
    }

    public long getNumberOfEntries() {
        return numberOfEntries;
    }

    public void setNumberOfEntries(long numberOfEntries) {
        this.numberOfEntries = numberOfEntries;
    }
}

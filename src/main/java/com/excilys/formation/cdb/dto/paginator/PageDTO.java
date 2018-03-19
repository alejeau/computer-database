package com.excilys.formation.cdb.dto.paginator;

import com.excilys.formation.cdb.dto.ModelDTO;

import java.util.List;

public class PageDTO<T extends ModelDTO> {
    protected List<T> content;
    protected long pageNumber;
    protected long maxPageNumber;
    protected long objectsPerPage;
    protected long numberOfEntries;

    public PageDTO() {
    }

    public PageDTO(List<T> content, long pageNumber, long maxPageNumber, long objectsPerPage, long numberOfEntries) {
        this.content = content;
        this.pageNumber = pageNumber;
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

    public long getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(long pageNumber) {
        this.pageNumber = pageNumber;
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

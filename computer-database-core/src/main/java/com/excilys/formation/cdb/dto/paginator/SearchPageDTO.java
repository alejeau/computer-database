package com.excilys.formation.cdb.dto.paginator;

import com.excilys.formation.cdb.dto.ModelDTO;

import java.util.List;

public class SearchPageDTO<T extends ModelDTO> extends PageDTO<T> {
    private String search = null;

    public SearchPageDTO() {
    }

    public SearchPageDTO(List<T> content, long pageNumber, long maxPageNumber, long objectsPerPage, long numberOfEntries, String search) {
        super(content, pageNumber, maxPageNumber, objectsPerPage, numberOfEntries);
        this.search = search;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}

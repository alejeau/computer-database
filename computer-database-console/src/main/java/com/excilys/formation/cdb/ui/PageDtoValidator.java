package com.excilys.formation.cdb.ui;

import com.excilys.formation.cdb.dto.ModelDTO;
import com.excilys.formation.cdb.dto.paginator.PageDTO;

public class PageDtoValidator {

    private static final long FIRST_PAGE = 0L;


    public static <T extends PageDTO<U>, U extends ModelDTO> T previous(T page) {
        long previous = page.getCurrentPageNumber() - 1;
        if (previous >= FIRST_PAGE) {
            page.setCurrentPageNumber(previous);
        }

        return page;
    }

    public static <T extends PageDTO<U>, U extends ModelDTO> T next(T page) {
        long next = page.getCurrentPageNumber() + 1;
        if (next <= page.getMaxPageNumber()) {
            page.setCurrentPageNumber(next);
        }

        return page;
    }

    public static <T extends PageDTO<U>, U extends ModelDTO> T goTo(T page, Long pageNumber) {
        if (pageNumber >= FIRST_PAGE || pageNumber <= page.getMaxPageNumber()) {
            page.setCurrentPageNumber(pageNumber);
        }

        return page;
    }

    public static <T extends PageDTO<U>, U extends ModelDTO> T first(T page) {
        page.setCurrentPageNumber(FIRST_PAGE);
        return page;
    }

    public static <T extends PageDTO<U>, U extends ModelDTO> T last(T page) {
        page.setCurrentPageNumber(page.getMaxPageNumber());
        return page;
    }

}

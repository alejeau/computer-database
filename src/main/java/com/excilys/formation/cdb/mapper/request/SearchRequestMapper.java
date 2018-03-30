package com.excilys.formation.cdb.mapper.request;

import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.paginator.ComputerSortedPage;
import com.excilys.formation.cdb.paginator.ComputerSortedSearchPage;
import com.excilys.formation.cdb.paginator.core.LimitValue;
import com.excilys.formation.cdb.paginator.core.Page;
import com.excilys.formation.cdb.servlets.constants.ComputerField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class SearchRequestMapper {

    private static final Logger LOG = LoggerFactory.getLogger(SearchRequestMapper.class);

    public static ComputerSortedSearchPage mapDoGet(HttpServletRequest request) throws ServiceException {
        LOG.debug("mapDoGet");
        ComputerSortedSearchPage computerSortedSearchPage;

        String search = request.getParameter("search");
        Long pageNb = UrlMapper.mapLongNumber(request, UrlFields.PAGE_NB, Page.FIRST_PAGE);
        LimitValue displayBy = UrlMapper.mapDisplayBy(request);
        ComputerField computerField = UrlMapper.mapToComputerFields(request, "field", ComputerField.COMPUTER_NAME);
        boolean ascending = UrlMapper.mapToBoolean(request, "ascending", true);

        computerSortedSearchPage = new ComputerSortedSearchPage(search, displayBy, computerField, ascending);
        computerSortedSearchPage.goToPage(pageNb);

        return computerSortedSearchPage;
    }
}

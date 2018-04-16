package com.excilys.formation.cdb.mapper.request;

import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.paginator.core.LimitValue;
import com.excilys.formation.cdb.paginator.core.Page;
import com.excilys.formation.cdb.paginator.pager.ComputerSortedSearchPage;
import com.excilys.formation.cdb.paginator.pager.PageFactory;
import com.excilys.formation.cdb.service.ComputerService;
import com.excilys.formation.cdb.servlets.constants.ComputerField;
import com.excilys.formation.cdb.servlets.constants.ServletParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class SearchRequestMapper {
    private static final Logger LOG = LoggerFactory.getLogger(SearchRequestMapper.class);

    private PageFactory pageFactory;

    @Autowired
    public SearchRequestMapper(PageFactory pageFactory) {
        this.pageFactory = pageFactory;
    }

    public ComputerSortedSearchPage mapDoGet(HttpServletRequest request) throws ServiceException {
        LOG.debug("mapDoGet");
        ComputerSortedSearchPage computerSortedSearchPage;

        String search = request.getParameter(ServletParameter.SEARCH);
        Long pageNb = UrlMapper.mapLongNumber(request, ServletParameter.PAGE_NB, Page.FIRST_PAGE);
        LimitValue displayBy = UrlMapper.mapDisplayBy(request, LimitValue.TEN);
        ComputerField computerField = UrlMapper.mapToComputerFields(request, ServletParameter.FIELD, ComputerField.COMPUTER_NAME);
        boolean ascending = UrlMapper.mapToBoolean(request, ServletParameter.ASCENDING, true);

        computerSortedSearchPage = pageFactory.createComputerSortedSearchPage(search, displayBy, computerField, ascending);
        computerSortedSearchPage.goToPage(pageNb);

        return computerSortedSearchPage;
    }
}

package com.excilys.formation.cdb.mapper.request;

import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.paginator.ComputerPage;
import com.excilys.formation.cdb.paginator.ComputerSortedPage;
import com.excilys.formation.cdb.paginator.core.LimitValue;
import com.excilys.formation.cdb.paginator.core.Page;
import com.excilys.formation.cdb.servlets.constants.ComputerField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class DashboardRequestMapper {

    private static final Logger LOG = LoggerFactory.getLogger(DashboardRequestMapper.class);

    public static ComputerSortedPage mapDoGet(HttpServletRequest request) throws ServiceException {
        LOG.debug("mapDoGet");

        ComputerSortedPage computerSortedPage;

        Long pageNb = UrlMapper.mapLongNumber(request, UrlFields.PAGE_NB, Page.FIRST_PAGE);
        LimitValue displayBy = UrlMapper.mapDisplayBy(request);
        ComputerField computerField = UrlMapper.mapToComputerFields(request, "field", ComputerField.COMPUTER_NAME);
        boolean ascending = UrlMapper.mapToBoolean(request, "ascending", true);

        computerSortedPage = new ComputerSortedPage(displayBy, computerField, ascending);
        computerSortedPage.goToPage(pageNb);

        return computerSortedPage;
    }
}

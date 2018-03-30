package com.excilys.formation.cdb.mapper.request;

import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.servlets.constants.ServletParameter;
import com.excilys.formation.cdb.paginator.ComputerSortedPage;
import com.excilys.formation.cdb.paginator.core.LimitValue;
import com.excilys.formation.cdb.paginator.core.Page;
import com.excilys.formation.cdb.servlets.constants.ComputerField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class DashboardRequestMapper {
    private static final Logger LOG = LoggerFactory.getLogger(DashboardRequestMapper.class);

    private DashboardRequestMapper() {
    }

    public static ComputerSortedPage mapDoGet(HttpServletRequest request) throws ServiceException {
        LOG.debug("mapDoGet");

        ComputerSortedPage computerSortedPage;

        Long pageNb = UrlMapper.mapLongNumber(request, ServletParameter.PAGE_NB, Page.FIRST_PAGE);
        LimitValue displayBy = UrlMapper.mapDisplayBy(request, LimitValue.TEN);
        ComputerField computerField = UrlMapper.mapToComputerFields(request, ServletParameter.FIELD, ComputerField.COMPUTER_NAME);
        boolean ascending = UrlMapper.mapToBoolean(request, ServletParameter.ASCENDING, true);

        computerSortedPage = new ComputerSortedPage(displayBy, computerField, ascending);
        computerSortedPage.goToPage(pageNb);

        return computerSortedPage;
    }
}

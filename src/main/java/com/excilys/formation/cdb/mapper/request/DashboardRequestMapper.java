package com.excilys.formation.cdb.mapper.request;

import com.excilys.formation.cdb.exceptions.UnauthorizedLimitValueException;
import com.excilys.formation.cdb.mapper.LimitValueMapper;
import com.excilys.formation.cdb.paginator.ComputerPage;
import com.excilys.formation.cdb.paginator.core.LimitValue;
import com.excilys.formation.cdb.paginator.core.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class DashboardRequestMapper {

    private static final Logger LOG = LoggerFactory.getLogger(DashboardRequestMapper.class);

    public static ComputerPage mapDoGet(HttpServletRequest request) {
        LOG.debug("mapDoGet");

        ComputerPage computerPage;

        String stringPageNb = request.getParameter(UrlFields.URL_PAGE_NB);
        String stringDisplayBy = request.getParameter(UrlFields.URL_DISPLAY_BY);

        LimitValue displayBy = LimitValue.TEN;
        Long pageNb = Page.FIRST_PAGE.longValue();

        if ((stringPageNb != null) && !stringPageNb.isEmpty() && stringPageNb.matches("[0-9]+")) {
            pageNb = Long.parseLong(stringPageNb);
        } else {
            LOG.error("Can't parse '" + stringPageNb + "' as a Long!");
        }

        if ((stringDisplayBy != null) && !stringDisplayBy.isEmpty() && stringDisplayBy.matches("[0-9]+")) {
            try {
                displayBy = LimitValueMapper.toLimitValue(stringDisplayBy);
            } catch (UnauthorizedLimitValueException e) {
                LOG.error(e.getMessage());
            }
        }

        computerPage = new ComputerPage(displayBy);
        computerPage.goToPage(pageNb);

        return computerPage;
    }
}

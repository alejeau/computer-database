package com.excilys.formation.cdb.mapper.request;

import com.excilys.formation.cdb.paginator.ComputerPage;
import com.excilys.formation.cdb.paginator.core.LimitValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class DashboardRequestMapper {

    private static final Logger LOG = LoggerFactory.getLogger(DashboardRequestMapper.class);

    public static ComputerPage mapDoGet(HttpServletRequest request) {
        LOG.debug("mapDoGet");

        ComputerPage computerPage;

        Long pageNb = UrlMapper.mapPageNumber(request);
        LimitValue displayBy = UrlMapper.mapDisplayBy(request);

        computerPage = new ComputerPage(displayBy);
        computerPage.goToPage(pageNb);

        return computerPage;
    }
}

package com.excilys.formation.cdb.mapper.request;

import com.excilys.formation.cdb.exceptions.MapperException;
import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.paginator.ComputerSortedPage;
import com.excilys.formation.cdb.paginator.core.LimitValue;
import com.excilys.formation.cdb.paginator.core.Page;
import com.excilys.formation.cdb.service.ComputerService;
import com.excilys.formation.cdb.servlets.constants.ComputerField;
import com.excilys.formation.cdb.servlets.constants.ServletParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.excilys.formation.cdb.servlets.constants.ServletParameter.SELECTION;

public class DashboardRequestMapper {
    private static final Logger LOG = LoggerFactory.getLogger(DashboardRequestMapper.class);

    private DashboardRequestMapper() {
    }

    public static void mapDoPost(HttpServletRequest request, ComputerService computerService) throws MapperException {
        LOG.debug("mapDoPost");
        String selection = request.getParameter(SELECTION);

        if (selection != null && !selection.isEmpty()) {
            String[] stringToDelete = selection.split(",");
            List<Long> idList = new ArrayList<>(stringToDelete.length);
            Arrays.stream(stringToDelete)
                    .filter(s -> s.matches("[0-9]+"))
                    .map(Long::valueOf)
                    .forEach(idList::add);
            try {
                computerService.deleteComputers(idList);
            } catch (ServiceException e) {
                LOG.error("{}", e);
                throw new MapperException(e);
            }
        }
    }

    public static ComputerSortedPage mapDoGet(HttpServletRequest request, ComputerService computerService) throws ServiceException {
        LOG.debug("mapDoGet");

        ComputerSortedPage computerSortedPage;

        Long pageNb = UrlMapper.mapLongNumber(request, ServletParameter.PAGE_NB, Page.FIRST_PAGE);
        LimitValue displayBy = UrlMapper.mapDisplayBy(request, LimitValue.TEN);
        ComputerField computerField = UrlMapper.mapToComputerFields(request, ServletParameter.FIELD, ComputerField.COMPUTER_NAME);
        boolean ascending = UrlMapper.mapToBoolean(request, ServletParameter.ASCENDING, true);

        computerSortedPage = new ComputerSortedPage(displayBy, computerField, ascending);
        computerSortedPage.setComputerService(computerService);
        computerSortedPage.goToPage(pageNb);

        return computerSortedPage;
    }
}

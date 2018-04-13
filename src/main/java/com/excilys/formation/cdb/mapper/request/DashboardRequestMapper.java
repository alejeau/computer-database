package com.excilys.formation.cdb.mapper.request;

import com.excilys.formation.cdb.exceptions.MapperException;
import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.paginator.core.LimitValue;
import com.excilys.formation.cdb.paginator.core.Page;
import com.excilys.formation.cdb.paginator.pager.ComputerSortedPage;
import com.excilys.formation.cdb.paginator.pager.PageFactory;
import com.excilys.formation.cdb.service.ComputerService;
import com.excilys.formation.cdb.servlets.constants.ComputerField;
import com.excilys.formation.cdb.servlets.constants.ServletParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.excilys.formation.cdb.servlets.constants.ServletParameter.SELECTION;

@Component
public class DashboardRequestMapper {
    private static final Logger LOG = LoggerFactory.getLogger(DashboardRequestMapper.class);

    private ComputerService computerService;
    private PageFactory pageFactory;

    @Autowired
    public DashboardRequestMapper(ComputerService computerService, PageFactory pageFactory) {
        this.computerService = computerService;
        this.pageFactory = pageFactory;
    }

    public void mapDoPost(HttpServletRequest request) throws MapperException {
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

    public ComputerSortedPage mapDoGet(HttpServletRequest request) throws ServiceException {
        LOG.debug("mapDoGet");

        ComputerSortedPage computerSortedPage;

        Long pageNb = UrlMapper.mapLongNumber(request, ServletParameter.PAGE_NB, Page.FIRST_PAGE);
        LimitValue displayBy = UrlMapper.mapDisplayBy(request, LimitValue.TEN);
        ComputerField computerField = UrlMapper.mapToComputerFields(request, ServletParameter.FIELD, ComputerField.COMPUTER_NAME);
        boolean ascending = UrlMapper.mapToBoolean(request, ServletParameter.ASCENDING, true);

        computerSortedPage = pageFactory.createComputerSortedPage(displayBy, computerField, ascending);
        computerSortedPage.goToPage(pageNb);

        return computerSortedPage;
    }
}

package com.excilys.formation.cdb.servlets;

import com.excilys.formation.cdb.dto.model.ComputerDTO;
import com.excilys.formation.cdb.dto.paginator.SearchPageDTO;
import com.excilys.formation.cdb.exceptions.MapperException;
import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.mapper.page.PageMapper;
import com.excilys.formation.cdb.mapper.request.DashboardRequestMapper;
import com.excilys.formation.cdb.mapper.request.SearchRequestMapper;
import com.excilys.formation.cdb.paginator.pager.ComputerSortedSearchPage;
import com.excilys.formation.cdb.paginator.core.LimitValue;
import com.excilys.formation.cdb.service.ComputerService;
import com.excilys.formation.cdb.servlets.constants.Paths;
import com.excilys.formation.cdb.servlets.constants.Views;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.excilys.formation.cdb.servlets.constants.ServletParameter.CURRENT_PATH;
import static com.excilys.formation.cdb.servlets.constants.ServletParameter.IS_ASCENDING;
import static com.excilys.formation.cdb.servlets.constants.ServletParameter.LIMIT_VALUES;
import static com.excilys.formation.cdb.servlets.constants.ServletParameter.ORDER_BY;
import static com.excilys.formation.cdb.servlets.constants.ServletParameter.PAGE_DTO;
import static com.excilys.formation.cdb.servlets.constants.ServletParameter.SEARCH;
import static com.excilys.formation.cdb.servlets.constants.ServletParameter.SEARCH_FIELD;

@Controller
public class ServletSearch extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(ServletSearch.class);

    @Autowired
    private ComputerService computerService;

    @Autowired
    private SearchRequestMapper searchRequestMapper;

    @Autowired
    private DashboardRequestMapper dashboardRequestMapper;

    public ServletSearch() {
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("doGet");
        String search = request.getParameter(SEARCH);

        if (!StringUtils.isBlank(search)) {
            try {
                ComputerSortedSearchPage computerSortedSearchPage = searchRequestMapper.mapDoGet(request);
                request = setRequest(request, computerSortedSearchPage);
            } catch (ServiceException e) {
                LOG.error("{}", e);
                throw new ServletException(e.getMessage(), e);
            }
        }
        this.getServletContext().getRequestDispatcher(Views.DASHBOARD).forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("doPost");
        try {
            dashboardRequestMapper.mapDoPost(request);
        } catch (MapperException e) {
            throw new ServletException(e);
        }
        this.doGet(request, response);
    }

    private HttpServletRequest setRequest(HttpServletRequest request, ComputerSortedSearchPage cssp) throws ServiceException {
        LOG.debug("setRequest");
        // Setting the paths
        request.setAttribute(CURRENT_PATH, Paths.PATH_SEARCH_COMPUTER);

        SearchPageDTO<ComputerDTO> computerSearchPageDTO = PageMapper
                .toSearchPageDTO(cssp, computerService.getNumberOfComputersWithName(cssp.getSearch()));
        request.setAttribute(PAGE_DTO, computerSearchPageDTO);
        request.setAttribute(ORDER_BY, cssp.getOrderBy().getValue());
        request.setAttribute(IS_ASCENDING, cssp.isAscending());
        request.setAttribute(SEARCH_FIELD, cssp.getSearch());
        request.setAttribute(LIMIT_VALUES, LimitValue.toLongList());

        return request;
    }

}

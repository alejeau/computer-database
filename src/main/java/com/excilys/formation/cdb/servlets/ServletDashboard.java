package com.excilys.formation.cdb.servlets;

import com.excilys.formation.cdb.dto.model.ComputerDTO;
import com.excilys.formation.cdb.dto.paginator.PageDTO;
import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.mapper.page.PageMapper;
import com.excilys.formation.cdb.mapper.request.DashboardRequestMapper;
import com.excilys.formation.cdb.paginator.ComputerSortedPage;
import com.excilys.formation.cdb.paginator.core.LimitValue;
import com.excilys.formation.cdb.service.ComputerService;
import com.excilys.formation.cdb.service.impl.ComputerServiceImpl;
import com.excilys.formation.cdb.servlets.constants.Paths;
import com.excilys.formation.cdb.servlets.constants.Views;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServletDashboard extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(ServletDashboard.class);
    private static ComputerService computerService = ComputerServiceImpl.INSTANCE;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("doGet");
        try {
            ComputerSortedPage computerSortedPage = DashboardRequestMapper.mapDoGet(request);
            request = setRequest(request, computerSortedPage);
        } catch (ServiceException e) {
            LOG.error("{}", e);
            throw new ServletException(e);
        }
        this.getServletContext().getRequestDispatcher(Views.DASHBOARD).forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("doPost");
        String selection = request.getParameter("selection");
        LOG.debug("selection: " + selection);

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
                throw new ServletException(e);
            }
        }
        this.doGet(request, response);
    }

    private static HttpServletRequest setRequest(HttpServletRequest request, ComputerSortedPage computerSortedPage) throws ServiceException{
        LOG.debug("setRequest");
        // Setting the paths
        request.setAttribute("currentPath", Paths.PATH_DASHBOARD);

        PageDTO<ComputerDTO> computerPageDTO = PageMapper.toPageDTO(computerSortedPage, computerService.getNumberOfComputers());
        request.setAttribute("pageDTO", computerPageDTO);
        request.setAttribute("orderBy", computerSortedPage.getOrderBy().getValue());
        request.setAttribute("isAscending", computerSortedPage.isAscending());
        request.setAttribute("limitValues", LimitValue.toLongList());

        return request;
    }
}
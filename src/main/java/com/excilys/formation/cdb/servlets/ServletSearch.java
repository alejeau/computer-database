package com.excilys.formation.cdb.servlets;

import com.excilys.formation.cdb.dto.model.ComputerDTO;
import com.excilys.formation.cdb.dto.paginator.SearchPageDTO;
import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.mapper.page.PageMapper;
import com.excilys.formation.cdb.mapper.request.UrlFields;
import com.excilys.formation.cdb.mapper.request.UrlMapper;
import com.excilys.formation.cdb.paginator.ComputerSearchPage;
import com.excilys.formation.cdb.paginator.core.LimitValue;
import com.excilys.formation.cdb.paginator.core.Page;
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

public class ServletSearch extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(ServletSearch.class);
    private static ComputerService computerService = ComputerServiceImpl.INSTANCE;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("doGet");

        String search = request.getParameter("search");
        LOG.debug("Search: {}", search);
        SearchPageDTO<ComputerDTO> searchPageDTO = new SearchPageDTO<>();

        if ((search != null) && !search.isEmpty()) {
            try {
                LimitValue displayBy = UrlMapper.mapDisplayBy(request);
                Long pageNb = UrlMapper.mapLongNumber(request, UrlFields.PAGE_NB, Page.FIRST_PAGE);

                ComputerSearchPage computerSearchPage = computerService.getComputer(search, displayBy);
                computerSearchPage.goToPage(pageNb);
                searchPageDTO = PageMapper.toSearchPageDTO(computerSearchPage, computerService.getNumberOfComputersWithName(search));
            } catch (ServiceException e) {
                LOG.error("{}", e);
                throw new ServletException(e.getMessage(), e);
            }
        }
        request = setRequest(request, searchPageDTO);
        this.getServletContext().getRequestDispatcher(Views.DASHBOARD).forward(request, response);
    }

    private static HttpServletRequest setRequest(HttpServletRequest request, SearchPageDTO<ComputerDTO> searchPageDTO) {
        LOG.debug("setRequest");
        request.setAttribute("currentPath", Paths.PATH_SEARCH_COMPUTER);
        request.setAttribute("pageDTO", searchPageDTO);
        request.setAttribute("searchField", searchPageDTO.getSearch());
        request.setAttribute("limitValues", LimitValue.toLongList());
        return request;
    }

}

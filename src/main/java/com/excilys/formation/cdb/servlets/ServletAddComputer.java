package com.excilys.formation.cdb.servlets;

import com.excilys.formation.cdb.dto.model.CompanyDTO;
import com.excilys.formation.cdb.dto.model.ComputerDTO;
import com.excilys.formation.cdb.dto.paginator.PageDTO;
import com.excilys.formation.cdb.mapper.model.CompanyMapper;
import com.excilys.formation.cdb.mapper.page.PageMapper;
import com.excilys.formation.cdb.mapper.request.DashboardRequestMapper;
import com.excilys.formation.cdb.paginator.ComputerPage;
import com.excilys.formation.cdb.paginator.core.LimitValue;
import com.excilys.formation.cdb.service.CompanyService;
import com.excilys.formation.cdb.service.ComputerService;
import com.excilys.formation.cdb.servlets.constants.Paths;
import com.excilys.formation.cdb.servlets.constants.Views;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ServletAddComputer extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(ServletAddComputer.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request = setRequest(request);
        this.getServletContext().getRequestDispatcher(Views.ADD_COMPUTER).forward(request, response);
    }

    private static HttpServletRequest setRequest(HttpServletRequest request) {
        // Setting the paths
        request.setAttribute("pathDashboard", Paths.PATH_DASHBOARD);
        request.setAttribute("pathAddComputer", Paths.PATH_ADD_COMPUTER);
        // Setting the vars
        request.setAttribute("currentPath", Paths.PATH_DASHBOARD);

        List<CompanyDTO> companyList = CompanyMapper.mapList(CompanyService.INSTANCE.getCompanies());
        LOG.debug("CompanyList.size(): " + companyList.size());
        request.setAttribute("companyList", companyList);

        return request;
    }
}
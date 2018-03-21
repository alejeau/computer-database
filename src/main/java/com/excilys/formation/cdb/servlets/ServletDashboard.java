package com.excilys.formation.cdb.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.cdb.dto.model.ComputerDTO;
import com.excilys.formation.cdb.dto.paginator.PageDTO;
import com.excilys.formation.cdb.mapper.model.ComputerMapper;
import com.excilys.formation.cdb.mapper.page.PageMapper;
import com.excilys.formation.cdb.paginator.ComputerPage;
import com.excilys.formation.cdb.service.ComputerService;
import com.excilys.formation.cdb.servlets.constants.Paths;
import com.excilys.formation.cdb.servlets.constants.Views;

public class ServletDashboard extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ComputerPage computerPage = new ComputerPage();
        computerPage.first();
        PageDTO<ComputerDTO> computerPageDTO = PageMapper.toPageDTO(computerPage, ComputerService.INSTANCE.getNumberOfComputers());

        request = setRequest(request, computerPageDTO);
		this.getServletContext().getRequestDispatcher(Views.DASHBOARD).forward(request, response);
	}

    private static HttpServletRequest setRequest(HttpServletRequest request, PageDTO<ComputerDTO> computerPageDTO) {
        // Setting the paths
        request.setAttribute("pathDashboard", Paths.PATH_DASHBOARD);
        request.setAttribute("pathAddComputer", Paths.PATH_COMPUTER_ADD);
        request.setAttribute("pageDTO", computerPageDTO);
        // Setting the vars
        request.setAttribute("currentPath", Paths.PATH_DASHBOARD);

        return request;
    }
}
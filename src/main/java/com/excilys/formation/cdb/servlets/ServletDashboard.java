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
import com.excilys.formation.cdb.servlets.constants.Views;

public class ServletDashboard extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ComputerPage computerPage = new ComputerPage();
        computerPage.first();
//        System.out.println(computerPage.getPage());
//        computerPage.getPage().forEach(System.out::println);
		request.setAttribute("computerPageDTO", PageMapper.toPageDTO(computerPage, ComputerService.INSTANCE.getNumberOfComputers()));
		this.getServletContext().getRequestDispatcher(Views.DASHBOARD).forward(request, response);
	}	

}
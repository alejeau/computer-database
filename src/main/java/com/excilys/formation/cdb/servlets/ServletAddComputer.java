package com.excilys.formation.cdb.servlets;

import com.excilys.formation.cdb.dto.model.CompanyDTO;
import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.exceptions.ValidationException;
import com.excilys.formation.cdb.mapper.model.CompanyMapper;
import com.excilys.formation.cdb.mapper.request.UrlMapper;
import com.excilys.formation.cdb.mapper.validators.ErrorMapper;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.service.CompanyService;
import com.excilys.formation.cdb.service.ComputerService;
import com.excilys.formation.cdb.servlets.constants.Paths;
import com.excilys.formation.cdb.servlets.constants.Views;
import com.excilys.formation.cdb.validators.ComputerValidator;
import com.excilys.formation.cdb.validators.core.Error;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ServletAddComputer extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(ServletAddComputer.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request = setRequest(request, null);
        } catch (ServiceException e) {
            LOG.error("{}", e);
            throw new ServletException(e.getMessage(), e);
        }
        this.getServletContext().getRequestDispatcher(Views.ADD_COMPUTER).forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Error> errorList;

        String computerName = request.getParameter("computerName");
        LOG.debug("retrieved computerName: " + computerName);
        String introduced = request.getParameter("introduced");
        LOG.debug("retrieved introduced: " + introduced);
        String discontinued = request.getParameter("discontinued");
        LOG.debug("retrieved discontinued: " + discontinued);

        errorList = ComputerValidator.INSTANCE.validate(computerName, introduced, discontinued);

        try {
            if (errorList == null) {
                Long companyId = Long.valueOf(request.getParameter("companyId"));
                Company company = CompanyService.INSTANCE.getCompany(companyId);
                Computer computer = new Computer.Builder().
                        name(computerName)
                        .introduced(introduced)
                        .discontinued(discontinued)
                        .company(company)
                        .build();
                try {
                    ComputerService.INSTANCE.persistComputer(computer);
                } catch (ValidationException e) {
                    LOG.error(e.getMessage());
                }
            } else {
                errorList.stream()
                        .filter(Objects::nonNull)
                        .forEach(error -> LOG.error(error.toString()));
            }

            setRequest(request, errorList);
        } catch (ServiceException e) {
            LOG.error("{}", e);
            throw new ServletException(e.getMessage(), e);
        }
        this.getServletContext().getRequestDispatcher(Views.ADD_COMPUTER).forward(request, response);
    }

    private static HttpServletRequest setRequest(HttpServletRequest request, List<Error> errorList) throws ServiceException {
        request.setAttribute("pathDashboard", Paths.PATH_DASHBOARD);
        request.setAttribute("pathAddComputer", Paths.PATH_ADD_COMPUTER);
        request.setAttribute("currentPath", Paths.PATH_DASHBOARD);

        List<CompanyDTO> companyList = CompanyMapper.mapList(CompanyService.INSTANCE.getCompanies());
        request.setAttribute("companyList", companyList);

        HashMap<String, String> hashMap = ErrorMapper.toHashMap(errorList);
        request.setAttribute("errorMap", hashMap);

        request.setAttribute("targetPageNumber", UrlMapper.mapPageNumber(request));
        request.setAttribute("targetDisplayBy", UrlMapper.mapDisplayBy(request).getValue());

        return request;
    }
}
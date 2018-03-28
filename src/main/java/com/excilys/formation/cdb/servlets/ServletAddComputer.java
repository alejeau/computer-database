package com.excilys.formation.cdb.servlets;

import com.excilys.formation.cdb.dto.model.CompanyDTO;
import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.exceptions.ValidationException;
import com.excilys.formation.cdb.mapper.model.CompanyMapper;
import com.excilys.formation.cdb.mapper.request.UrlFields;
import com.excilys.formation.cdb.mapper.request.UrlMapper;
import com.excilys.formation.cdb.mapper.validators.ErrorMapper;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.paginator.core.Page;
import com.excilys.formation.cdb.service.impl.CompanyServiceImpl;
import com.excilys.formation.cdb.service.impl.ComputerServiceImpl;
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
        LOG.debug("doGet");
        try {
            request = setRequest(request, null, false);
        } catch (ServiceException e) {
            LOG.error("{}", e);
            throw new ServletException(e.getMessage(), e);
        }
        this.getServletContext().getRequestDispatcher(Views.ADD_COMPUTER).forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("doPost");
        List<Error> errorList;

        String computerName = request.getParameter("computerName");
        LOG.debug("retrieved computerName: " + computerName);
        String introduced = request.getParameter("introduced");
        LOG.debug("retrieved introduced: " + introduced);
        String discontinued = request.getParameter("discontinued");
        LOG.debug("retrieved discontinued: " + discontinued);

        boolean displaySuccessMessage = false;
        errorList = ComputerValidator.INSTANCE.validate(computerName, introduced, discontinued);

        try {
            if (errorList == null) {
                displaySuccessMessage = true;
                Long companyId = Long.valueOf(request.getParameter("companyId"));
                Company company = CompanyServiceImpl.INSTANCE.getCompany(companyId);
                Computer computer = new Computer.Builder().
                        name(computerName)
                        .introduced(introduced)
                        .discontinued(discontinued)
                        .company(company)
                        .build();
                try {
                    ComputerServiceImpl.INSTANCE.persistComputer(computer);
                } catch (ValidationException e) {
                    LOG.error(e.getMessage());
                }
            } else {
                errorList.stream()
                        .filter(Objects::nonNull)
                        .forEach(error -> LOG.error(error.toString()));
            }

            setRequest(request, errorList, displaySuccessMessage);
        } catch (ServiceException e) {
            LOG.error("{}", e);
            throw new ServletException(e.getMessage(), e);
        }
        this.getServletContext().getRequestDispatcher(Views.ADD_COMPUTER).forward(request, response);
    }

    private static HttpServletRequest setRequest(HttpServletRequest request, List<Error> errorList, boolean displaySuccessMessage) throws ServiceException {
        LOG.debug("setRequest");
        request.setAttribute("pathDashboard", Paths.PATH_DASHBOARD);
        request.setAttribute("pathAddComputer", Paths.PATH_ADD_COMPUTER);
        request.setAttribute("currentPath", Paths.PATH_DASHBOARD);

        request.setAttribute("displaySuccessMessage", displaySuccessMessage);
        List<CompanyDTO> companyList = CompanyMapper.mapList(CompanyServiceImpl.INSTANCE.getCompanies());
        request.setAttribute("companyList", companyList);

        HashMap<String, String> hashMap = ErrorMapper.toHashMap(errorList);
        request.setAttribute("errorMap", hashMap);

        request.setAttribute("targetPageNumber", UrlMapper.mapLongNumber(request, UrlFields.PAGE_NB, Page.FIRST_PAGE));
        request.setAttribute("targetDisplayBy", UrlMapper.mapDisplayBy(request).getValue());

        return request;
    }
}
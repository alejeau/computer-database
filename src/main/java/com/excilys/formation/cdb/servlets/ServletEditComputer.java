package com.excilys.formation.cdb.servlets;

import com.excilys.formation.cdb.dto.model.CompanyDTO;
import com.excilys.formation.cdb.dto.model.ComputerDTO;
import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.exceptions.ValidationException;
import com.excilys.formation.cdb.mapper.model.CompanyMapper;
import com.excilys.formation.cdb.mapper.model.ComputerMapper;
import com.excilys.formation.cdb.mapper.request.UrlFields;
import com.excilys.formation.cdb.mapper.request.UrlMapper;
import com.excilys.formation.cdb.mapper.validators.ErrorMapper;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.paginator.core.Page;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ServletEditComputer extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(ServletEditComputer.class);
    private static final Long NO_COMPUTER = -1L;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("doGet");
        Long computerId = UrlMapper.mapLongNumber(request, UrlFields.COMPUTER_ID, NO_COMPUTER);
        LOG.debug("Computer ID: {}", computerId);
        try {
            if (!computerId.equals(NO_COMPUTER) && ComputerService.INSTANCE.getComputer(computerId) != null) {
                ComputerDTO computerDTO = ComputerMapper.toDTO(ComputerService.INSTANCE.getComputer(computerId));
                LOG.debug("{}", computerDTO);
                request = setRequest(request, computerDTO, new ArrayList<>(), false);
            } else {
                response.sendRedirect(Paths.PATH_DASHBOARD);
                return;
            }
        } catch (ServiceException e) {
            LOG.error("{}", e);
            throw new ServletException(e.getMessage(), e);
        }
        this.getServletContext().getRequestDispatcher(Views.EDIT_COMPUTER).forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("doPost");
        List<Error> errorList;

        String stringComputerId = request.getParameter("computerId");
        LOG.debug("retrieved computerId: " + stringComputerId);
        String computerName = request.getParameter("computerName");
        LOG.debug("retrieved computerName: " + computerName);
        String introduced = request.getParameter("introduced");
        LOG.debug("retrieved introduced: " + introduced);
        String discontinued = request.getParameter("discontinued");
        LOG.debug("retrieved discontinued: " + discontinued);

        Computer computer = null;
        boolean displaySuccessMessage = false;
        errorList = ComputerValidator.INSTANCE.validate(computerName, introduced, discontinued);
        try {
            if (errorList == null) {
                Long computerId = UrlMapper.mapLongNumber(request, UrlFields.COMPUTER_ID, NO_COMPUTER);
                if (!computerId.equals(NO_COMPUTER) && ComputerService.INSTANCE.getComputer(computerId) != null) {
                    displaySuccessMessage = true;
                    Long companyId = Long.valueOf(request.getParameter("companyId"));
                    Company company = CompanyService.INSTANCE.getCompany(companyId);
                    computer = new Computer.Builder()
                            .id(computerId)
                            .name(computerName)
                            .introduced(introduced)
                            .discontinued(discontinued)
                            .company(company)
                            .build();
                    try {
                        ComputerService.INSTANCE.updateComputer(computer);
                    } catch (ValidationException e) {
                        LOG.error(e.getMessage());
                    }
                } else {
                    response.sendRedirect(Paths.PATH_DASHBOARD);
                    return;
                }
            } else {
                errorList.stream()
                        .filter(Objects::nonNull)
                        .forEach(error -> LOG.error(error.toString()));
            }

            setRequest(request, ComputerMapper.toDTO(computer), errorList, displaySuccessMessage);
        } catch (ServiceException e) {
            LOG.error("{}", e);
            throw new ServletException(e.getMessage(), e);
        }
        this.getServletContext().getRequestDispatcher(Views.EDIT_COMPUTER).forward(request, response);
    }

    private static HttpServletRequest setRequest(HttpServletRequest request, ComputerDTO computerDTO, List<Error> errorList, boolean displaySuccessMessage) throws ServiceException {
        LOG.debug("setRequest");
        request.setAttribute("pathDashboard", Paths.PATH_DASHBOARD);
        request.setAttribute("pathAddComputer", Paths.PATH_ADD_COMPUTER);
        request.setAttribute("currentPath", Paths.PATH_EDIT_COMPUTER);

        // URL attributes
        request.setAttribute("targetPageNumber", UrlMapper.mapLongNumber(request, UrlFields.PAGE_NB, Page.FIRST_PAGE));
        request.setAttribute("targetDisplayBy", UrlMapper.mapDisplayBy(request).getValue());

        request.setAttribute("displaySuccessMessage", displaySuccessMessage);
        request.setAttribute("computerDTO", computerDTO);

        List<CompanyDTO> companyList = CompanyMapper.mapList(CompanyService.INSTANCE.getCompanies());
        request.setAttribute("companyList", companyList);

        HashMap<String, String> hashMap = ErrorMapper.toHashMap(errorList);
        request.setAttribute("errorMap", hashMap);


        return request;
    }
}
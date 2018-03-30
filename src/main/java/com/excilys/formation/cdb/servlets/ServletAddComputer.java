package com.excilys.formation.cdb.servlets;

import com.excilys.formation.cdb.dto.model.CompanyDTO;
import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.exceptions.ValidationException;
import com.excilys.formation.cdb.mapper.model.CompanyMapper;
import com.excilys.formation.cdb.mapper.request.UrlMapper;
import com.excilys.formation.cdb.mapper.validators.ErrorMapper;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.paginator.core.LimitValue;
import com.excilys.formation.cdb.paginator.core.Page;
import com.excilys.formation.cdb.service.CompanyService;
import com.excilys.formation.cdb.service.ComputerService;
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

import static com.excilys.formation.cdb.servlets.constants.ServletParameter.COMPANY_ID;
import static com.excilys.formation.cdb.servlets.constants.ServletParameter.COMPANY_LIST;
import static com.excilys.formation.cdb.servlets.constants.ServletParameter.COMPUTER_NAME;
import static com.excilys.formation.cdb.servlets.constants.ServletParameter.CURRENT_PATH;
import static com.excilys.formation.cdb.servlets.constants.ServletParameter.DISCONTINUED;
import static com.excilys.formation.cdb.servlets.constants.ServletParameter.DISPLAY_SUCCESS_MESSAGE;
import static com.excilys.formation.cdb.servlets.constants.ServletParameter.ERROR_MAP;
import static com.excilys.formation.cdb.servlets.constants.ServletParameter.INTRODUCED;
import static com.excilys.formation.cdb.servlets.constants.ServletParameter.PAGE_NB;
import static com.excilys.formation.cdb.servlets.constants.ServletParameter.TARGET_DISPLAY_BY;
import static com.excilys.formation.cdb.servlets.constants.ServletParameter.TARGET_PAGE_NUMBER;

public class ServletAddComputer extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(ServletAddComputer.class);
    private static ComputerService computerService = ComputerServiceImpl.INSTANCE;
    private static CompanyService companyService = CompanyServiceImpl.INSTANCE;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("doGet");
        try {
            request = setRequest(request, null, false);
        } catch (ServiceException e) {
            LOG.error("{}", e);
            throw new ServletException(e);
        }
        this.getServletContext().getRequestDispatcher(Views.ADD_COMPUTER).forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("doPost");
        List<Error> errorList;

        String computerName = request.getParameter(COMPUTER_NAME);
        String introduced = request.getParameter(INTRODUCED);
        String discontinued = request.getParameter(DISCONTINUED);

        boolean displaySuccessMessage = false;
        errorList = ComputerValidator.INSTANCE.validate(computerName, introduced, discontinued);

        try {
            if (errorList == null) {
                displaySuccessMessage = true;
                Long companyId = Long.valueOf(request.getParameter(COMPANY_ID));
                Company company = companyService.getCompany(companyId);
                Computer computer = new Computer.Builder().
                        name(computerName)
                        .introduced(introduced)
                        .discontinued(discontinued)
                        .company(company)
                        .build();

                computerService.persistComputer(computer);
            } else {
                errorList.stream()
                        .filter(Objects::nonNull)
                        .map(Error::toString)
                        .forEach(LOG::error);
            }

            setRequest(request, errorList, displaySuccessMessage);
        } catch (ServiceException | ValidationException e) {
            LOG.error("{}", e);
            throw new ServletException(e.getMessage(), e);
        }
        this.getServletContext().getRequestDispatcher(Views.ADD_COMPUTER).forward(request, response);
    }

    private static HttpServletRequest setRequest(HttpServletRequest request, List<Error> errorList, boolean displaySuccessMessage) throws ServiceException {
        LOG.debug("setRequest");
        request.setAttribute(CURRENT_PATH, Paths.PATH_ADD_COMPUTER);

        request.setAttribute(DISPLAY_SUCCESS_MESSAGE, displaySuccessMessage);
        List<CompanyDTO> companyList = CompanyMapper.mapList(companyService.getCompanies());
        request.setAttribute(COMPANY_LIST, companyList);

        HashMap<String, String> hashMap = ErrorMapper.toHashMap(errorList);
        request.setAttribute(ERROR_MAP, hashMap);

        request.setAttribute(TARGET_PAGE_NUMBER, UrlMapper.mapLongNumber(request, PAGE_NB, Page.FIRST_PAGE));
        request.setAttribute(TARGET_DISPLAY_BY, UrlMapper.mapDisplayBy(request, LimitValue.TEN).getValue());

        return request;
    }
}
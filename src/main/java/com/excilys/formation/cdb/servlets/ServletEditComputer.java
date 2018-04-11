package com.excilys.formation.cdb.servlets;

import com.excilys.formation.cdb.dto.model.CompanyDTO;
import com.excilys.formation.cdb.dto.model.ComputerDTO;
import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.exceptions.ValidationException;
import com.excilys.formation.cdb.mapper.model.CompanyMapper;
import com.excilys.formation.cdb.mapper.model.ComputerMapper;
import com.excilys.formation.cdb.mapper.request.UrlMapper;
import com.excilys.formation.cdb.mapper.validators.ErrorMapper;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.paginator.core.LimitValue;
import com.excilys.formation.cdb.paginator.core.Page;
import com.excilys.formation.cdb.service.CompanyService;
import com.excilys.formation.cdb.service.ComputerService;
import com.excilys.formation.cdb.servlets.constants.Paths;
import com.excilys.formation.cdb.servlets.constants.ServletParameter;
import com.excilys.formation.cdb.servlets.constants.Views;
import com.excilys.formation.cdb.validators.ComputerValidator;
import com.excilys.formation.cdb.validators.core.Error;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.excilys.formation.cdb.servlets.constants.ServletParameter.COMPANY_ID;
import static com.excilys.formation.cdb.servlets.constants.ServletParameter.COMPANY_LIST;
import static com.excilys.formation.cdb.servlets.constants.ServletParameter.COMPUTER_DTO;
import static com.excilys.formation.cdb.servlets.constants.ServletParameter.COMPUTER_NAME;
import static com.excilys.formation.cdb.servlets.constants.ServletParameter.DISCONTINUED;
import static com.excilys.formation.cdb.servlets.constants.ServletParameter.DISPLAY_SUCCESS_MESSAGE;
import static com.excilys.formation.cdb.servlets.constants.ServletParameter.ERROR_MAP;
import static com.excilys.formation.cdb.servlets.constants.ServletParameter.INTRODUCED;
import static com.excilys.formation.cdb.servlets.constants.ServletParameter.TARGET_DISPLAY_BY;
import static com.excilys.formation.cdb.servlets.constants.ServletParameter.TARGET_PAGE_NUMBER;

@Component
public class ServletEditComputer extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(ServletEditComputer.class);
    private static final Long NO_COMPUTER = -1L;

    @Autowired
    private ComputerService computerService;

    @Autowired
    private CompanyService companyService;

    public ServletEditComputer() {
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("doGet");
        Long computerId = UrlMapper.mapLongNumber(request, ServletParameter.COMPUTER_ID, NO_COMPUTER);
        try {
            if (!computerId.equals(NO_COMPUTER) && computerService.getComputer(computerId) != null) {
                ComputerDTO computerDTO = ComputerMapper.toDTO(computerService.getComputer(computerId));
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

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("doPost");
        List<Error> errorList;

        String computerName = request.getParameter(COMPUTER_NAME);
        String introduced = request.getParameter(INTRODUCED);
        String discontinued = request.getParameter(DISCONTINUED);

        Computer computer = null;
        boolean displaySuccessMessage = false;
        errorList = ComputerValidator.validate(computerName, introduced, discontinued);
        try {
            if (errorList == null) {
                Long computerId = UrlMapper.mapLongNumber(request, ServletParameter.COMPUTER_ID, NO_COMPUTER);
                if (!computerId.equals(NO_COMPUTER) && computerService.getComputer(computerId) != null) {
                    displaySuccessMessage = true;
                    Long companyId = Long.valueOf(request.getParameter(COMPANY_ID));
                    Company company = companyService.getCompany(companyId);
                    computer = new Computer.Builder()
                            .id(computerId)
                            .name(computerName)
                            .introduced(introduced)
                            .discontinued(discontinued)
                            .company(company)
                            .build();
                    try {
                        computerService.updateComputer(computer);
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
                        .map(Error::toString)
                        .forEach(LOG::error);
            }

            setRequest(request, ComputerMapper.toDTO(computer), errorList, displaySuccessMessage);
        } catch (ServiceException e) {
            LOG.error("{}", e);
            throw new ServletException(e.getMessage(), e);
        }
        this.getServletContext().getRequestDispatcher(Views.EDIT_COMPUTER).forward(request, response);
    }

    private HttpServletRequest setRequest(HttpServletRequest request, ComputerDTO computerDTO, List<Error> errorList, boolean displaySuccessMessage) throws ServiceException {
        LOG.debug("setRequest");
        request.setAttribute(ServletParameter.CURRENT_PATH, Paths.PATH_EDIT_COMPUTER);

        // URL attributes
        request.setAttribute(TARGET_PAGE_NUMBER, UrlMapper.mapLongNumber(request, ServletParameter.PAGE_NB, Page.FIRST_PAGE));
        request.setAttribute(TARGET_DISPLAY_BY, UrlMapper.mapDisplayBy(request, LimitValue.TEN).getValue());

        request.setAttribute(DISPLAY_SUCCESS_MESSAGE, displaySuccessMessage);
        request.setAttribute(COMPUTER_DTO, computerDTO);

        List<CompanyDTO> companyList = CompanyMapper.mapList(companyService.getCompanies());
        request.setAttribute(COMPANY_LIST, companyList);

        Map<String, String> hashMap = ErrorMapper.toHashMap(errorList);
        request.setAttribute(ERROR_MAP, hashMap);


        return request;
    }
}
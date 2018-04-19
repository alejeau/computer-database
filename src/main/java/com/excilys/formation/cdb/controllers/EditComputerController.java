package com.excilys.formation.cdb.controllers;

import com.excilys.formation.cdb.controllers.constants.ControllerParameters;
import com.excilys.formation.cdb.controllers.constants.Paths;
import com.excilys.formation.cdb.controllers.constants.Views;
import com.excilys.formation.cdb.dto.model.CompanyDTO;
import com.excilys.formation.cdb.dto.model.ComputerDTO;
import com.excilys.formation.cdb.exceptions.ControllerException;
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
import com.excilys.formation.cdb.validators.ComputerValidator;
import com.excilys.formation.cdb.validators.core.Error;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.excilys.formation.cdb.controllers.constants.ControllerParameters.COMPANY_LIST;
import static com.excilys.formation.cdb.controllers.constants.ControllerParameters.COMPUTER_DTO;
import static com.excilys.formation.cdb.controllers.constants.ControllerParameters.DISPLAY_SUCCESS_MESSAGE;
import static com.excilys.formation.cdb.controllers.constants.ControllerParameters.ERROR_MAP;
import static com.excilys.formation.cdb.controllers.constants.ControllerParameters.TARGET_DISPLAY_BY;
import static com.excilys.formation.cdb.controllers.constants.ControllerParameters.TARGET_PAGE_NUMBER;

@Controller
@RequestMapping(Paths.LOCAL_PATH_EDIT_COMPUTER)
public class EditComputerController {
    private static final Logger LOG = LoggerFactory.getLogger(EditComputerController.class);
    private static final Long NO_COMPUTER = -1L;

    private ComputerService computerService;
    private CompanyService companyService;
    private DashboardController dashboardController;

    @Autowired
    public EditComputerController(ComputerService computerService, CompanyService companyService, DashboardController dashboardController) {
        this.computerService = computerService;
        this.companyService = companyService;
        this.dashboardController = dashboardController;
    }

    @GetMapping
    public ModelAndView get(@RequestParam Map<String, String> params) throws ControllerException {
        LOG.debug("get");
        ModelAndView modelAndView = new ModelAndView(Views.EDIT_COMPUTER);
        Long computerId = UrlMapper.mapLongNumber(params, ControllerParameters.COMPUTER_ID, NO_COMPUTER);
        try {
            if (!computerId.equals(NO_COMPUTER) && computerService.getComputer(computerId) != null) {
                ComputerDTO computerDTO = ComputerMapper.toDTO(computerService.getComputer(computerId));
                modelAndView = setModelAndView(modelAndView, params, computerDTO, new ArrayList<>(), false);
            } else {
                return dashboardController.get(params);
            }
        } catch (ServiceException e) {
            LOG.error("{}", e);
            throw new ControllerException(e);
        }
        return modelAndView;
    }

    @PostMapping
    public ModelAndView post(@ModelAttribute("edit") ComputerDTO computerDTO, @RequestParam Map<String, String> params) throws ControllerException {
        LOG.debug("post");
        ModelAndView modelAndView = new ModelAndView(Views.EDIT_COMPUTER);
        List<Error> errorList;

        Computer computer = null;
        boolean displaySuccessMessage = false;
        errorList = ComputerValidator.validate(computerDTO);
        Long computerId = computerDTO.getId();

        try {
            if (errorList == null) {
                if (!computerId.equals(NO_COMPUTER) && computerService.getComputer(computerId) != null) {
                    displaySuccessMessage = true;
                    Long companyId = computerDTO.getCompanyId();
                    Company company = companyService.getCompany(companyId);
                    computer = ComputerMapper.toComputer(computerDTO, company);
                    computerService.updateComputer(computer);
                } else {
                    return dashboardController.get(params);
                }
            } else {
                // if there are errors, log them...
                errorList.stream()
                        .filter(Objects::nonNull)
                        .map(Error::toString)
                        .forEach(LOG::error);
                // ... and return the old computer
                if (!computerId.equals(NO_COMPUTER)) {
                    computer = computerService.getComputer(computerId);
                }
            }

            modelAndView = setModelAndView(modelAndView, params, ComputerMapper.toDTO(computer), errorList, displaySuccessMessage);
        } catch (ServiceException | ValidationException e) {
            LOG.error("{}", e);
            throw new ControllerException(e);
        }
        return modelAndView;
    }

    private ModelAndView setModelAndView(ModelAndView modelAndView, Map<String, String> params, ComputerDTO computerDTO, List<Error> errorList, boolean displaySuccessMessage) throws ServiceException {
        LOG.debug("setModelAndView");
        modelAndView.addObject(ControllerParameters.CURRENT_PATH, Paths.ABSOLUTE_PATH_EDIT_COMPUTER);

        // URL attributes
        modelAndView.addObject(TARGET_PAGE_NUMBER, UrlMapper.mapLongNumber(params, ControllerParameters.PAGE_NB, Page.FIRST_PAGE));
        modelAndView.addObject(TARGET_DISPLAY_BY, UrlMapper.mapDisplayBy(params, LimitValue.TEN).getValue());

        modelAndView.addObject(DISPLAY_SUCCESS_MESSAGE, displaySuccessMessage);
        modelAndView.addObject(COMPUTER_DTO, computerDTO);

        List<CompanyDTO> companyList = CompanyMapper.mapList(companyService.getCompanies());
        modelAndView.addObject(COMPANY_LIST, companyList);

        Map<String, String> hashMap = ErrorMapper.toHashMap(errorList);
        modelAndView.addObject(ERROR_MAP, hashMap);

        return modelAndView;
    }
}
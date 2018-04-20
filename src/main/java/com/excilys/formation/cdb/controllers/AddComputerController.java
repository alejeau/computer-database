package com.excilys.formation.cdb.controllers;

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

import javax.servlet.ServletException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.excilys.formation.cdb.controllers.constants.ControllerParameters.COMPANY_LIST;
import static com.excilys.formation.cdb.controllers.constants.ControllerParameters.CURRENT_PATH;
import static com.excilys.formation.cdb.controllers.constants.ControllerParameters.DISPLAY_SUCCESS_MESSAGE;
import static com.excilys.formation.cdb.controllers.constants.ControllerParameters.ERROR_MAP;
import static com.excilys.formation.cdb.controllers.constants.ControllerParameters.PAGE_NB;
import static com.excilys.formation.cdb.controllers.constants.ControllerParameters.TARGET_DISPLAY_BY;
import static com.excilys.formation.cdb.controllers.constants.ControllerParameters.TARGET_PAGE_NUMBER;

@Controller
@RequestMapping(Paths.LOCAL_PATH_ADD_COMPUTER)
public class AddComputerController {
    private static final Logger LOG = LoggerFactory.getLogger(AddComputerController.class);

    private ComputerService computerService;
    private CompanyService companyService;

    @Autowired
    public AddComputerController(ComputerService computerService, CompanyService companyService) {
        this.computerService = computerService;
        this.companyService = companyService;
    }

    @GetMapping
    public ModelAndView get(@RequestParam Map<String, String> params) throws ControllerException {
        LOG.debug("get");
        ModelAndView modelAndView = new ModelAndView(Views.ADD_COMPUTER);
        try {
            modelAndView = setModelAndView(modelAndView, params, null, false);
        } catch (ServiceException e) {
            LOG.error("{}", e);
            throw new ControllerException(e);
        }
        return modelAndView;
    }

    private ModelAndView setModelAndView(ModelAndView modelAndView, Map<String, String> params, List<Error> errorList, boolean displaySuccessMessage) throws ServiceException {
        LOG.debug("setRequest");
        modelAndView.addObject(CURRENT_PATH, Paths.ABSOLUTE_PATH_ADD_COMPUTER);

        modelAndView.addObject(DISPLAY_SUCCESS_MESSAGE, displaySuccessMessage);
        List<CompanyDTO> companyList = CompanyMapper.mapList(companyService.getCompanies());
        modelAndView.addObject(COMPANY_LIST, companyList);

        Map<String, String> hashMap = ErrorMapper.toHashMap(errorList);
        modelAndView.addObject(ERROR_MAP, hashMap);

        modelAndView.addObject(TARGET_PAGE_NUMBER, UrlMapper.mapLongNumber(params, PAGE_NB, Page.FIRST_PAGE));
        modelAndView.addObject(TARGET_DISPLAY_BY, UrlMapper.mapDisplayBy(params, LimitValue.TEN).getValue());

        return modelAndView;
    }

    @PostMapping
    public ModelAndView post(@ModelAttribute("add") @Valid ComputerDTO computerDTO, @RequestParam Map<String, String> params) throws ServletException, IOException {
        LOG.debug("post");

        ModelAndView modelAndView = new ModelAndView(Views.ADD_COMPUTER);
        List<Error> errorList;

        boolean displaySuccessMessage = false;
        errorList = ComputerValidator.validate(computerDTO);

        try {
            if (errorList == null) {
                displaySuccessMessage = true;
                Company company = companyService.getCompany(computerDTO.getCompanyId());
                Computer computer = ComputerMapper.toComputer(computerDTO, company);
                computerService.persistComputer(computer);
            } else {
                errorList.stream()
                        .filter(Objects::nonNull)
                        .map(Error::toString)
                        .forEach(LOG::error);
            }

            setModelAndView(modelAndView, params, errorList, displaySuccessMessage);
        } catch (ServiceException | ValidationException e) {
            LOG.error("{}", e);
            throw new ServletException(e.getMessage(), e);
        }
        return modelAndView;
    }
}
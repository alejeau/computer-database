package com.excilys.formation.cdb.controllers;

import com.excilys.formation.cdb.dto.model.ComputerDTO;
import com.excilys.formation.cdb.exceptions.ControllerException;
import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.model.constants.ControllerParameters;
import com.excilys.formation.cdb.model.constants.LimitValue;
import com.excilys.formation.cdb.model.constants.Paths;
import com.excilys.formation.cdb.model.constants.Views;
import com.excilys.formation.cdb.service.UserService;
import com.excilys.formation.cdb.service.paginator.core.Page;
import com.excilys.formation.cdb.service.validators.core.Error;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(Paths.LOCAL_PATH_LOGIN)
public class AuthController {
    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    private UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView get(@RequestParam Map<String, String> params) throws ControllerException {
        LOG.debug("get");
        ModelAndView modelAndView = new ModelAndView(Views.LOGIN);
        try {
            modelAndView = setModelAndView(modelAndView, params, null, false);
        } catch (ServiceException e) {
            LOG.error("{}", e);
            throw new ControllerException(e);
        }
        return modelAndView;
    }

    @PostMapping
    public ModelAndView post(@Validated @ModelAttribute("add") ComputerDTO computerDTO,
                             BindingResult bindingResult,
                             @RequestParam Map<String, String> params) throws ControllerException {
        LOG.debug("post");

        List<Error> errorList = null;
        boolean displaySuccessMessage = true;
        ModelAndView modelAndView = new ModelAndView(Views.LOGIN);
        return modelAndView;
    }

    private ModelAndView setModelAndView(ModelAndView modelAndView, Map<String, String> params, List<Error> errorList, boolean displaySuccessMessage) throws ServiceException {
        LOG.debug("setRequest");
        modelAndView.addObject(ControllerParameters.CURRENT_PATH, Paths.ABSOLUTE_PATH_LOGIN);

        modelAndView.addObject(ControllerParameters.TARGET_PAGE_NUMBER, Page.FIRST_PAGE);
        modelAndView.addObject(ControllerParameters.TARGET_DISPLAY_BY, LimitValue.TEN.getValue());

        return modelAndView;
    }
}

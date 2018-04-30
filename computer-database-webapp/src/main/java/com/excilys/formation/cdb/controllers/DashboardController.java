package com.excilys.formation.cdb.controllers;

import com.excilys.formation.cdb.dto.model.ComputerDTO;
import com.excilys.formation.cdb.dto.paginator.PageDTO;
import com.excilys.formation.cdb.exceptions.ControllerException;
import com.excilys.formation.cdb.exceptions.MapperException;
import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.mapper.page.PageMapper;
import com.excilys.formation.cdb.mapper.request.DashboardRequestMapper;
import com.excilys.formation.cdb.model.constants.ControllerParameters;
import com.excilys.formation.cdb.model.constants.LimitValue;
import com.excilys.formation.cdb.model.constants.Paths;
import com.excilys.formation.cdb.model.constants.Views;
import com.excilys.formation.cdb.service.ComputerService;
import com.excilys.formation.cdb.service.paginator.pager.ComputerSortedPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping(Paths.LOCAL_PATH_DASHBOARD)
public class DashboardController {
    private static final Logger LOG = LoggerFactory.getLogger(DashboardController.class);

    private ComputerService computerService;
    private DashboardRequestMapper dashboardRequestMapper;

    @Autowired
    public DashboardController(ComputerService computerService, DashboardRequestMapper dashboardRequestMapper) {
        this.computerService = computerService;
        this.dashboardRequestMapper = dashboardRequestMapper;
    }

    @PostMapping
    public ModelAndView post(@RequestParam Map<String, String> params) throws ControllerException {
        LOG.debug("post");
        try {
            dashboardRequestMapper.mapPost(params);
        } catch (MapperException e) {
            throw new ControllerException(e);
        }
        return get(params);
    }

    @GetMapping
    public ModelAndView get(@RequestParam Map<String, String> params) throws ControllerException {
        LOG.debug("get");
        ModelAndView modelAndView = new ModelAndView(Views.DASHBOARD);
        try {
            ComputerSortedPage computerSortedPage = dashboardRequestMapper.mapGet(params);
            modelAndView = setModelAndView(modelAndView, computerSortedPage);
        } catch (ServiceException e) {
            LOG.error("{}", e);
            throw new ControllerException(e);
        }
        return modelAndView;
    }

    private ModelAndView setModelAndView(ModelAndView modelAndView, ComputerSortedPage computerSortedPage) throws ServiceException {
        LOG.debug("setModelAndView");
        // Setting the paths
        modelAndView.addObject(ControllerParameters.CURRENT_PATH, Paths.ABSOLUTE_PATH_DASHBOARD);

        PageDTO<ComputerDTO> computerPageDTO = PageMapper.toPageDTO(computerSortedPage, computerService.getNumberOfComputers());
        modelAndView.addObject(ControllerParameters.PAGE_DTO, computerPageDTO);
        modelAndView.addObject(ControllerParameters.ORDER_BY, computerSortedPage.getOrderBy().getValue());
        modelAndView.addObject(ControllerParameters.IS_ASCENDING, computerSortedPage.isAscending());
        modelAndView.addObject(ControllerParameters.LIMIT_VALUES, LimitValue.toLongList());

        return modelAndView;
    }
}
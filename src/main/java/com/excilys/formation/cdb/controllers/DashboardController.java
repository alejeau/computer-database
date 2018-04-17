package com.excilys.formation.cdb.controllers;

import com.excilys.formation.cdb.dto.model.ComputerDTO;
import com.excilys.formation.cdb.dto.paginator.PageDTO;
import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.mapper.page.PageMapper;
import com.excilys.formation.cdb.mapper.request.DashboardRequestMapper;
import com.excilys.formation.cdb.paginator.pager.ComputerSortedPage;
import com.excilys.formation.cdb.paginator.core.LimitValue;
import com.excilys.formation.cdb.service.ComputerService;
import com.excilys.formation.cdb.controllers.constants.Paths;
import com.excilys.formation.cdb.controllers.constants.Views;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

import static com.excilys.formation.cdb.controllers.constants.ControllerParameters.CURRENT_PATH;
import static com.excilys.formation.cdb.controllers.constants.ControllerParameters.IS_ASCENDING;
import static com.excilys.formation.cdb.controllers.constants.ControllerParameters.LIMIT_VALUES;
import static com.excilys.formation.cdb.controllers.constants.ControllerParameters.ORDER_BY;
import static com.excilys.formation.cdb.controllers.constants.ControllerParameters.PAGE_DTO;

@Controller
@RequestMapping("/access")
public class DashboardController {
    private static final Logger LOG = LoggerFactory.getLogger(DashboardController.class);

    @Autowired
    private ComputerService computerService;
    
    @Autowired
    private DashboardRequestMapper dashboardRequestMapper;

    public DashboardController() {
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView get(@RequestParam Map<String, String> params) {
        LOG.debug("get");
        ModelAndView modelAndView = new ModelAndView(Views.DASHBOARD);
        try {
            ComputerSortedPage computerSortedPage = dashboardRequestMapper.mapGet(params);
            modelAndView = setModelAndView(modelAndView, computerSortedPage);
        } catch (ServiceException e) {
            LOG.error("{}", e);
        }
        return modelAndView;
    }

//    @Override
//    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        LOG.debug("doPost");
//        try {
//            dashboardRequestMapper.mapDoPost(request);
//        } catch (MapperException e) {
//            throw new ServletException(e);
//        }
//        this.doGet(request, response);
//    }

    private ModelAndView setModelAndView(ModelAndView modelAndView, ComputerSortedPage computerSortedPage) throws ServiceException {
        LOG.debug("setModelAndView");
        // Setting the paths
        modelAndView.addObject(CURRENT_PATH, Paths.PATH_DASHBOARD);

        PageDTO<ComputerDTO> computerPageDTO = PageMapper.toPageDTO(computerSortedPage, computerService.getNumberOfComputers());
        modelAndView.addObject(PAGE_DTO, computerPageDTO);
        modelAndView.addObject(ORDER_BY, computerSortedPage.getOrderBy().getValue());
        modelAndView.addObject(IS_ASCENDING, computerSortedPage.isAscending());
        modelAndView.addObject(LIMIT_VALUES, LimitValue.toLongList());

        return modelAndView;
    }
}
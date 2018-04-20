package com.excilys.formation.cdb.controllers;

import com.excilys.formation.cdb.controllers.constants.Paths;
import com.excilys.formation.cdb.controllers.constants.Views;
import com.excilys.formation.cdb.dto.model.ComputerDTO;
import com.excilys.formation.cdb.dto.paginator.SearchPageDTO;
import com.excilys.formation.cdb.exceptions.ControllerException;
import com.excilys.formation.cdb.exceptions.MapperException;
import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.mapper.page.PageMapper;
import com.excilys.formation.cdb.mapper.request.DashboardRequestMapper;
import com.excilys.formation.cdb.mapper.request.SearchRequestMapper;
import com.excilys.formation.cdb.paginator.core.LimitValue;
import com.excilys.formation.cdb.paginator.pager.ComputerSortedSearchPage;
import com.excilys.formation.cdb.service.ComputerService;
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

import static com.excilys.formation.cdb.controllers.constants.ControllerParameters.CURRENT_PATH;
import static com.excilys.formation.cdb.controllers.constants.ControllerParameters.IS_ASCENDING;
import static com.excilys.formation.cdb.controllers.constants.ControllerParameters.LIMIT_VALUES;
import static com.excilys.formation.cdb.controllers.constants.ControllerParameters.ORDER_BY;
import static com.excilys.formation.cdb.controllers.constants.ControllerParameters.PAGE_DTO;
import static com.excilys.formation.cdb.controllers.constants.ControllerParameters.SEARCH_FIELD;

@Controller
@RequestMapping(Paths.LOCAL_PATH_SEARCH_COMPUTER)
public class SearchController {
    private static final Logger LOG = LoggerFactory.getLogger(SearchController.class);

    private ComputerService computerService;
    private SearchRequestMapper searchRequestMapper;
    private DashboardRequestMapper dashboardRequestMapper;

    @Autowired
    public SearchController(ComputerService computerService, SearchRequestMapper searchRequestMapper, DashboardRequestMapper dashboardRequestMapper) {
        this.computerService = computerService;
        this.searchRequestMapper = searchRequestMapper;
        this.dashboardRequestMapper = dashboardRequestMapper;
    }

    @GetMapping
    public ModelAndView get(@RequestParam Map<String, String> params) throws ControllerException {
        LOG.debug("get");
        ModelAndView modelAndView = new ModelAndView(Views.DASHBOARD);
        try {
            ComputerSortedSearchPage computerSortedSearchPage = searchRequestMapper.mapGet(params);
            modelAndView = setModelAndView(modelAndView, computerSortedSearchPage);
        } catch (ServiceException e) {
            LOG.error("{}", e);
            throw new ControllerException(e);
        }
        return modelAndView;
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

    private ModelAndView setModelAndView(ModelAndView modelAndView, ComputerSortedSearchPage cssp) throws ServiceException {
        LOG.debug("setRequest");
        // Setting the paths
        modelAndView.addObject(CURRENT_PATH, Paths.ABSOLUTE_PATH_SEARCH_COMPUTER);

        SearchPageDTO<ComputerDTO> computerSearchPageDTO = PageMapper
                .toSearchPageDTO(cssp, computerService.getNumberOfComputersWithName(cssp.getSearch()));
        modelAndView.addObject(PAGE_DTO, computerSearchPageDTO);
        modelAndView.addObject(ORDER_BY, cssp.getOrderBy().getValue());
        modelAndView.addObject(IS_ASCENDING, cssp.isAscending());
        modelAndView.addObject(SEARCH_FIELD, cssp.getSearch());
        modelAndView.addObject(LIMIT_VALUES, LimitValue.toLongList());

        return modelAndView;
    }

}

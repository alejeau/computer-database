package com.excilys.formation.cdb.controllers;

import com.excilys.formation.cdb.dto.model.ComputerDTO;
import com.excilys.formation.cdb.dto.paginator.SearchPageDTO;
import com.excilys.formation.cdb.exceptions.ControllerException;
import com.excilys.formation.cdb.exceptions.MapperException;
import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.mapper.page.PageMapper;
import com.excilys.formation.cdb.mapper.request.DashboardRequestMapper;
import com.excilys.formation.cdb.mapper.request.SearchRequestMapper;
import com.excilys.formation.cdb.model.constants.ControllerParameters;
import com.excilys.formation.cdb.model.constants.LimitValue;
import com.excilys.formation.cdb.model.constants.Paths;
import com.excilys.formation.cdb.model.constants.Views;
import com.excilys.formation.cdb.service.ComputerService;
import com.excilys.formation.cdb.service.paginator.pager.ComputerSortedSearchPage;
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
            ComputerSortedSearchPage computerSortedSearchPage = searchRequestMapper.mapGet(params);
            modelAndView = setModelAndView(modelAndView, computerSortedSearchPage);
        } catch (ServiceException e) {
            LOG.error("{}", e);
            throw new ControllerException(e);
        }
        return modelAndView;
    }

    private ModelAndView setModelAndView(ModelAndView modelAndView, ComputerSortedSearchPage cssp) throws ServiceException {
        LOG.debug("setRequest");
        // Setting the paths
        modelAndView.addObject(ControllerParameters.CURRENT_PATH, Paths.ABSOLUTE_PATH_SEARCH_COMPUTER);

        SearchPageDTO<ComputerDTO> computerSearchPageDTO = PageMapper
                .toSearchPageDTO(cssp, computerService.getNumberOfComputersWithName(cssp.getSearch()));
        modelAndView.addObject(ControllerParameters.PAGE_DTO, computerSearchPageDTO);
        modelAndView.addObject(ControllerParameters.ORDER_BY, cssp.getOrderBy().getValue());
        modelAndView.addObject(ControllerParameters.IS_ASCENDING, cssp.isAscending());
        modelAndView.addObject(ControllerParameters.SEARCH_FIELD, cssp.getSearch());
        modelAndView.addObject(ControllerParameters.LIMIT_VALUES, LimitValue.toLongList());

        return modelAndView;
    }

}

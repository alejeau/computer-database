package com.excilys.formation.cdb.mapper.request;

import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.model.constants.ComputerField;
import com.excilys.formation.cdb.model.constants.ControllerParameters;
import com.excilys.formation.cdb.model.constants.LimitValue;
import com.excilys.formation.cdb.service.paginator.core.Page;
import com.excilys.formation.cdb.service.paginator.pager.ComputerSortedSearchPage;
import com.excilys.formation.cdb.service.paginator.pager.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SearchRequestMapper {
    private static final Logger LOG = LoggerFactory.getLogger(SearchRequestMapper.class);

    private PageFactory pageFactory;

    @Autowired
    public SearchRequestMapper(PageFactory pageFactory) {
        this.pageFactory = pageFactory;
    }

    public ComputerSortedSearchPage mapGet(Map<String, String> params) throws ServiceException {
        LOG.debug("mapGet");
        ComputerSortedSearchPage computerSortedSearchPage = null;

        String search = params.get(ControllerParameters.SEARCH);
        Long pageNb = UrlMapper.mapLongNumber(params, ControllerParameters.PAGE_NB, Page.FIRST_PAGE);
        LimitValue displayBy = UrlMapper.mapDisplayBy(params, LimitValue.TEN);
        ComputerField computerField = UrlMapper.mapToComputerFields(params, ControllerParameters.FIELD, ComputerField.COMPUTER_NAME);
        boolean ascending = UrlMapper.mapToBoolean(params, ControllerParameters.ASCENDING, true);

        computerSortedSearchPage = pageFactory.createComputerSortedSearchPage(search, displayBy, computerField, ascending);
        computerSortedSearchPage.goToPage(pageNb);

        return computerSortedSearchPage;
    }
}

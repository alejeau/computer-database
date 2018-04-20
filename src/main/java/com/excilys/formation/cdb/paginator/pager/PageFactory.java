package com.excilys.formation.cdb.paginator.pager;

import com.excilys.formation.cdb.controllers.constants.ComputerField;
import com.excilys.formation.cdb.paginator.core.LimitValue;

public interface PageFactory {

    ComputerPage createComputerPage();

    ComputerPage createComputerPage(LimitValue limitValue);

    ComputerSearchPage createComputerSearchPage();

    ComputerSearchPage createComputerSearchPage(String search);

    ComputerSearchPage createComputerSearchPage(String search, LimitValue limit);

    ComputerSortedPage createComputerSortedPage();

    ComputerSortedPage createComputerSortedPage(LimitValue limit);

    ComputerSortedPage createComputerSortedPage(LimitValue limit, ComputerField orderBy, boolean ascending);

    ComputerSortedSearchPage createComputerSortedSearchPage();

    ComputerSortedSearchPage createComputerSortedSearchPage(String search);

    ComputerSortedSearchPage createComputerSortedSearchPage(String search, LimitValue limit);

    ComputerSortedSearchPage createComputerSortedSearchPage(String search, LimitValue limit, ComputerField orderBy, boolean ascending);

    CompanyPage createCompanyPage();

    CompanyPage createCompanyPage(LimitValue limitValue);
}

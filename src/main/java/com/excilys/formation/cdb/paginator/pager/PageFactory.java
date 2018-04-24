package com.excilys.formation.cdb.paginator.pager;

import com.excilys.formation.cdb.paginator.core.LimitValue;

public interface PageFactory {

    ComputerPage createComputerPage();

    ComputerPage createComputerPage(LimitValue limitValue);

    ComputerSearchPage createComputerSearchPage();

    ComputerSearchPage createComputerSearchPage(String search);

    ComputerSearchPage createComputerSearchPage(String search, LimitValue limit);

    CompanyPage createCompanyPage();

    CompanyPage createCompanyPage(LimitValue limitValue);
}

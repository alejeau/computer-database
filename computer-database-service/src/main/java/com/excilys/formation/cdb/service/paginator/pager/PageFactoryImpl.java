package com.excilys.formation.cdb.service.paginator.pager;

import com.excilys.formation.cdb.model.constants.ComputerField;
import com.excilys.formation.cdb.model.constants.LimitValue;
import com.excilys.formation.cdb.service.CompanyService;
import com.excilys.formation.cdb.service.ComputerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PageFactoryImpl implements PageFactory {

    private CompanyService companyService;
    private ComputerService computerService;

    @Autowired
    public PageFactoryImpl(CompanyService companyService, ComputerService computerService) {
        this.companyService = companyService;
        this.computerService = computerService;
    }

    @Override
    public ComputerPage createComputerPage() {
        ComputerPage computerPage = new ComputerPage();
        computerPage.setComputerService(computerService);
        return computerPage;
    }

    @Override
    public ComputerPage createComputerPage(LimitValue limitValue) {
        ComputerPage computerPage = new ComputerPage(limitValue);
        computerPage.setComputerService(computerService);
        return computerPage;
    }

    @Override
    public ComputerSearchPage createComputerSearchPage() {
        ComputerSearchPage computerSearchPage = new ComputerSearchPage();
        computerSearchPage.setComputerService(computerService);
        return computerSearchPage;
    }

    @Override
    public ComputerSearchPage createComputerSearchPage(String search) {
        ComputerSearchPage computerSearchPage = new ComputerSearchPage(search);
        computerSearchPage.setComputerService(computerService);
        return computerSearchPage;
    }

    @Override
    public ComputerSearchPage createComputerSearchPage(String search, LimitValue limit) {
        ComputerSearchPage computerSearchPage = new ComputerSearchPage(search, limit);
        computerSearchPage.setComputerService(computerService);
        return computerSearchPage;
    }

    @Override
    public ComputerSortedPage createComputerSortedPage() {
        ComputerSortedPage computerSortedPage = new ComputerSortedPage();
        computerSortedPage.setComputerService(computerService);
        return computerSortedPage;
    }

    @Override
    public ComputerSortedPage createComputerSortedPage(LimitValue limit) {
        ComputerSortedPage computerSortedPage = new ComputerSortedPage(limit);
        computerSortedPage.setComputerService(computerService);
        return computerSortedPage;
    }

    @Override
    public ComputerSortedPage createComputerSortedPage(LimitValue limit, ComputerField orderBy, boolean ascending) {
        ComputerSortedPage computerSortedPage = new ComputerSortedPage(limit, orderBy, ascending);
        computerSortedPage.setComputerService(computerService);
        return computerSortedPage;
    }

    @Override
    public ComputerSortedSearchPage createComputerSortedSearchPage() {
        ComputerSortedSearchPage computerSortedSearchPage = new ComputerSortedSearchPage();
        computerSortedSearchPage.setComputerService(computerService);
        return computerSortedSearchPage;
    }

    @Override
    public ComputerSortedSearchPage createComputerSortedSearchPage(String search) {
        ComputerSortedSearchPage computerSortedSearchPage = new ComputerSortedSearchPage(search);
        computerSortedSearchPage.setComputerService(computerService);
        return computerSortedSearchPage;
    }

    @Override
    public ComputerSortedSearchPage createComputerSortedSearchPage(String search, LimitValue limit) {
        ComputerSortedSearchPage computerSortedSearchPage = new ComputerSortedSearchPage(search, limit);
        computerSortedSearchPage.setComputerService(computerService);
        return computerSortedSearchPage;
    }

    @Override
    public ComputerSortedSearchPage createComputerSortedSearchPage(String search, LimitValue limit, ComputerField orderBy, boolean ascending) {
        ComputerSortedSearchPage computerSortedSearchPage = new ComputerSortedSearchPage(search, limit, orderBy, ascending);
        computerSortedSearchPage.setComputerService(computerService);
        return computerSortedSearchPage;
    }

    @Override
    public CompanyPage createCompanyPage() {
        CompanyPage companyPage = new CompanyPage();
        companyPage.setCompanyService(companyService);
        return companyPage;
    }

    @Override
    public CompanyPage createCompanyPage(LimitValue limitValue) {
        CompanyPage companyPage = new CompanyPage(limitValue);
        companyPage.setCompanyService(companyService);
        return companyPage;
    }
}

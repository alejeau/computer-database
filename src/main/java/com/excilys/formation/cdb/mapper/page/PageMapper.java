package com.excilys.formation.cdb.mapper.page;

import com.excilys.formation.cdb.dto.model.CompanyDTO;
import com.excilys.formation.cdb.dto.model.ComputerDTO;
import com.excilys.formation.cdb.dto.paginator.PageDTO;
import com.excilys.formation.cdb.dto.paginator.SearchPageDTO;
import com.excilys.formation.cdb.exceptions.UnauthorizedLimitValueException;
import com.excilys.formation.cdb.mapper.LimitValueMapper;
import com.excilys.formation.cdb.mapper.model.ComputerMapper;
import com.excilys.formation.cdb.paginator.CompanyPage;
import com.excilys.formation.cdb.paginator.CompanySearchPage;
import com.excilys.formation.cdb.paginator.ComputerPage;
import com.excilys.formation.cdb.paginator.ComputerSearchPage;
import com.excilys.formation.cdb.paginator.core.LimitValue;

import java.util.ArrayList;
import java.util.List;

public class PageMapper {
    public static PageDTO<ComputerDTO> toPageDTO(ComputerPage computerPage, long numberOfComputers) {
        List<ComputerDTO> list = new ArrayList<>();
        computerPage.getPage().forEach(computer -> list.add(ComputerMapper.toDTO(computer)));

        return new PageDTO<ComputerDTO>(
                list,
                computerPage.getPageNumber(),
                computerPage.currentLastPageNumber(),
                computerPage.getLimit().getValue(),
                numberOfComputers);
    }

    public static SearchPageDTO<ComputerDTO> toSearchPageDTO(ComputerSearchPage computerSearchPage, long numberOfComputers) {
        List<ComputerDTO> list = new ArrayList<>();
        computerSearchPage.getPage().forEach(computer -> list.add(ComputerMapper.toDTO(computer)));

        return new SearchPageDTO<ComputerDTO>(
                list,
                computerSearchPage.getPageNumber(),
                computerSearchPage.currentLastPageNumber(),
                computerSearchPage.getLimit().getValue(),
                numberOfComputers,
                computerSearchPage.getSearch());
    }

    public static ComputerPage toComputerPage(PageDTO<ComputerDTO> pageDTO) throws UnauthorizedLimitValueException {
        ComputerPage computerPage = new ComputerPage();
        LimitValue limit = LimitValueMapper.toLimitValue(pageDTO.getObjectsPerPage());
        computerPage.setLimit(limit);
        computerPage.goToPage(pageDTO.getPageNumber());
        return computerPage;
    }

    public static CompanyPage toCompanyPage(PageDTO<CompanyDTO> pageDTO) throws UnauthorizedLimitValueException {
        CompanyPage companyPage = new CompanyPage();
        LimitValue limit = LimitValueMapper.toLimitValue(pageDTO.getObjectsPerPage());
        companyPage.setLimit(limit);
        companyPage.goToPage(pageDTO.getPageNumber());
        return companyPage;
    }

    public static ComputerSearchPage toComputerSearchPage(SearchPageDTO<ComputerDTO> searchPageDTO) throws UnauthorizedLimitValueException {
        LimitValue limit = LimitValueMapper.toLimitValue(searchPageDTO.getObjectsPerPage());
        ComputerSearchPage computerSearchPage = new ComputerSearchPage(searchPageDTO.getSearch(), limit);
        computerSearchPage.goToPage(searchPageDTO.getPageNumber());
        return computerSearchPage;
    }

    public static CompanySearchPage toCompanySearchPage(SearchPageDTO<CompanyDTO> searchPageDTO) throws UnauthorizedLimitValueException {
        LimitValue limit = LimitValueMapper.toLimitValue(searchPageDTO.getObjectsPerPage());
        CompanySearchPage companySearchPage = new CompanySearchPage(searchPageDTO.getSearch(), limit);
        companySearchPage.goToPage(searchPageDTO.getPageNumber());
        return companySearchPage;
    }
}

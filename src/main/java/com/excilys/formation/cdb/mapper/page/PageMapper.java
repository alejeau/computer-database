package com.excilys.formation.cdb.mapper.page;

import com.excilys.formation.cdb.dto.model.ComputerDTO;
import com.excilys.formation.cdb.dto.paginator.PageDTO;
import com.excilys.formation.cdb.dto.paginator.SearchPageDTO;
import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.mapper.model.ComputerMapper;
import com.excilys.formation.cdb.paginator.pager.ComputerPage;
import com.excilys.formation.cdb.paginator.pager.ComputerSearchPage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PageMapper {

    private PageMapper() {
    }

    public static PageDTO<ComputerDTO> toPageDTO(ComputerPage computerPage, long numberOfComputers) throws ServiceException {
        List<ComputerDTO> computerDTOList = new ArrayList<>();
        computerPage.getList()
                .stream()
                .map(ComputerMapper::toDTO)
                .forEach(computerDTOList::add);

        return new PageDTO<>(
                computerDTOList,
                computerPage.getPageNumber(),
                computerPage.currentLastPageNumber(),
                computerPage.getLimit().getValue(),
                numberOfComputers);
    }

    public static SearchPageDTO<ComputerDTO> toSearchPageDTO(ComputerSearchPage computerSearchPage, long numberOfComputers) throws ServiceException {
        List<ComputerDTO> computerDTOList = new ArrayList<>();
        computerSearchPage.getList()
                .stream()
                .map(ComputerMapper::toDTO)
                .forEach(computerDTOList::add);

        return new SearchPageDTO<>(
                computerDTOList,
                computerSearchPage.getPageNumber(),
                computerSearchPage.currentLastPageNumber(),
                computerSearchPage.getLimit().getValue(),
                numberOfComputers,
                computerSearchPage.getSearch());
    }
}

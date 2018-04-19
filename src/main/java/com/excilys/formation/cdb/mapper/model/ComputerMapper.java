package com.excilys.formation.cdb.mapper.model;

import com.excilys.formation.cdb.dto.model.ComputerDTO;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.DatePattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComputerMapper {

    private ComputerMapper() {
    }

    /**
     * Maps a Computer to ComputerDTO object
     *
     * @param computer the computer object to map
     * @return a ComputerDTO object if Computer is not null, an empty ComputerDTO otherwise
     */
    public static ComputerDTO toDTO(Computer computer) {
        ComputerDTO computerDTO = new ComputerDTO();
        if (computer != null) {
            computerDTO.setId(computer.getId());
            computerDTO.setName(computer.getName());

            if (computer.getIntroduced() != null) {
                computerDTO.setIntroduced(computer.getIntroduced().format(DatePattern.FORMATTER));
            }
            if (computer.getDiscontinued() != null) {
                computerDTO.setDiscontinued(computer.getDiscontinued().format(DatePattern.FORMATTER));
            }
            if (computer.getCompany() != null) {
                computerDTO.setCompanyName(computer.getCompany().getName());
            }
        }
        return computerDTO;
    }

    public static Computer toComputer(ComputerDTO computerDTO, Company company) {
        return new Computer.Builder()
                .id(computerDTO.getId())
                .name(computerDTO.getName())
                .introduced(computerDTO.getIntroduced())
                .discontinued(computerDTO.getDiscontinued())
                .company(company)
                .build();
    }
}

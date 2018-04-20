package com.excilys.formation.cdb.mapper.model;

import com.excilys.formation.cdb.dto.model.ComputerDTO;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.DatePattern;
import org.springframework.stereotype.Component;

@Component
public class ComputerMapper {

    /**
     * Maps a {@link Computer} to {@link ComputerDTO} object
     *
     * @param computer the {@link Computer} object to map
     * @return a {@link ComputerDTO} object if {@link Computer} is not null, an empty {@link ComputerDTO} otherwise
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

    /**
     * Maps a {@link ComputerDTO} and a {@link Company} to a {@link Computer}
     *
     * @param computerDTO the {@link ComputerDTO} to map
     * @param company     the Company of the {@link Computer}
     * @return a {@link Computer} with elements of {@link ComputerDTO} and the provided {@link Company}
     */
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

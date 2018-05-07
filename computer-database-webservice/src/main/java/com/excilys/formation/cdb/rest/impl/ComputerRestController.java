package com.excilys.formation.cdb.rest.impl;

import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.constants.Paths;
import com.excilys.formation.cdb.service.ComputerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController(Paths.REST_COMPUTER)
public class ComputerRestController {
    private static final Logger LOG = LoggerFactory.getLogger(ComputerRestController.class);

    private ComputerService computerService;

    @Autowired
    public ComputerRestController(ComputerService computerService) {
        this.computerService = computerService;
    }

    @GetMapping("/{id}")
    public Computer getComputerWithId(@PathVariable Long id) {
        Computer computer = null;
        try {
            computer = computerService.getComputer(id);
        } catch (ServiceException e) {
            LOG.error("{}", e);
        }
        return computer;
    }
}

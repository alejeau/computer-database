package com.excilys.formation.cdb.persistence.dao.impl;

import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistence.DatabaseField;
import com.excilys.formation.cdb.persistence.dao.ComputerDAO;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@EnableTransactionManagement
public class ComputerDaoImpl implements ComputerDAO {
    @Override
    public Long getNumberOfComputers() {
        throw new NotYetImplementedException();
    }

    @Override
    public Long getNumberOfComputersWithName(String name) {
        throw new NotYetImplementedException();
    }

    @Override
    public Computer getComputer(Long id) {
        throw new NotYetImplementedException();
    }

    @Override
    public List<Computer> getComputersWithName(String name, long index, Long limit) {
        throw new NotYetImplementedException();
    }

    @Override
    public List<Computer> getComputersWithNameOrderedBy(String name, long index, Long limit, DatabaseField computerField, boolean ascending) {
        throw new NotYetImplementedException();
    }

    @Override
    public List<Computer> getComputerList(long index, Long limit) {
        throw new NotYetImplementedException();
    }

    @Override
    public List<Computer> getComputerListOrderedBy(long index, Long limit, DatabaseField computerField, boolean ascending) {
        throw new NotYetImplementedException();
    }

    @Override
    public void updateComputer(Computer c) {
        throw new NotYetImplementedException();
    }

    @Override
    public Long persistComputer(Computer c) {
        throw new NotYetImplementedException();
    }

    @Override
    public void deleteComputer(Long id) {
        throw new NotYetImplementedException();
    }

    @Override
    public void deleteComputers(List<Long> idList) {
        throw new NotYetImplementedException();
    }

    @Override
    public void deleteComputersWhitCompanyId(Long companyId) {
        throw new NotYetImplementedException();
    }
}

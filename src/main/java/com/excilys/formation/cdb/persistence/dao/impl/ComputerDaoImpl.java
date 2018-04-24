package com.excilys.formation.cdb.persistence.dao.impl;

import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistence.DatabaseField;
import com.excilys.formation.cdb.persistence.dao.ComputerDAO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

@Repository
@Transactional
public class ComputerDaoImpl implements ComputerDAO {
    @Override
    public Long getNumberOfComputers() {
        throw new NotImplementedException();
    }

    @Override
    public Long getNumberOfComputersWithName(String name) {
        throw new NotImplementedException();
    }

    @Override
    public Computer getComputer(Long id) {
        throw new NotImplementedException();
    }

    @Override
    public List<Computer> getComputersWithName(String name, long index, Long limit) {
        throw new NotImplementedException();
    }

    @Override
    public List<Computer> getComputersWithNameOrderedBy(String name, long index, Long limit, DatabaseField computerField, boolean ascending) {
        throw new NotImplementedException();
    }

    @Override
    public List<Computer> getComputerList(long index, Long limit) {
        throw new NotImplementedException();
    }

    @Override
    public List<Computer> getComputerListOrderedBy(long index, Long limit, DatabaseField computerField, boolean ascending) {
        throw new NotImplementedException();
    }

    @Override
    public void updateComputer(Computer c) {

    }

    @Override
    public Long persistComputer(Computer c) {
        throw new NotImplementedException();
    }

    @Override
    public void deleteComputer(Long id) {

    }

    @Override
    public void deleteComputers(List<Long> idList) {

    }

    @Override
    public void deleteComputersWhitCompanyId(Long companyId) {

    }
}

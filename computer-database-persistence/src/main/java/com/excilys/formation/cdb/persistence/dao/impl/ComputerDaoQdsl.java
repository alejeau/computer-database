package com.excilys.formation.cdb.persistence.dao.impl;

import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.QCompany;
import com.excilys.formation.cdb.model.QComputer;
import com.excilys.formation.cdb.persistence.DatabaseField;
import com.excilys.formation.cdb.persistence.dao.ComputerDAO;
import com.querydsl.jpa.hibernate.HibernateDeleteClause;
import com.querydsl.jpa.hibernate.HibernateQuery;
import com.querydsl.jpa.hibernate.HibernateUpdateClause;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class ComputerDaoQdsl implements ComputerDAO {
    private static final Logger LOG = LoggerFactory.getLogger(ComputerDAO.class);

    private SessionFactory sessionFactory;
    private QComputer qComputer;
    private QCompany qCompany;

    @Autowired
    public ComputerDaoQdsl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.qComputer = QComputer.computer;
        this.qCompany = QCompany.company;
    }

    @Override
    public Long getNumberOfComputers() {
        Session session = sessionFactory.openSession();
        HibernateQuery<Computer> query = new HibernateQuery<>(session);
        Long result = query.select(qComputer.id)
                .from(qComputer)
                .fetchCount();
        session.close();
        return result;
    }

    @Override
    public Long getNumberOfComputersWithName(String name) {
        Session session = sessionFactory.openSession();
        HibernateQuery<Computer> query = new HibernateQuery<>(session);
        Long result = query.select(qComputer.id)
                .from(qComputer)
                .leftJoin(qComputer.company, qCompany)
                .where(qComputer.name.contains(name)
                        .or(qComputer.company.name.contains(name))
                )
                .fetchCount();
        session.close();
        return result;
    }

    @Override
    public Computer getComputer(Long id) {
        Session session = sessionFactory.openSession();
        HibernateQuery<Computer> query = new HibernateQuery<>(session);
        Computer result = query.select(qComputer)
                .from(qComputer)
                .leftJoin(qComputer.company, qCompany)
                .where(qComputer.id.eq(id))
                .fetchOne();
        session.close();
        return result;
    }

    @Override
    public List<Computer> getComputersWithName(String name, long index, Long limit) {
        return getComputersWithNameOrderedBy(name, index, limit, DatabaseField.COMPUTER_NAME, true);
    }

    @Override
    public List<Computer> getComputersWithNameOrderedBy(String name, long index, Long limit, DatabaseField computerField, boolean ascending) {
        Session session = sessionFactory.openSession();
        HibernateQuery<Computer> query = new HibernateQuery<>(session);
        query.select(qComputer)
                .from(qComputer)
                .leftJoin(qComputer.company, qCompany)
                .where(qComputer.name.contains(name)
                        .or(qComputer.company.name.contains(name))
                )
                .offset(index)
                .limit(limit);
        query = orderBy(query, computerField, ascending);

        if (query == null) {
            throw new IllegalStateException("The HibernateQuery must not be null!");
        }

        List<Computer> computerList = query.fetch();
        session.close();
        return computerList;
    }

    @Override
    public List<Computer> getComputerList(long index, Long limit) {
        return getComputerListOrderedBy(index, limit, DatabaseField.COMPUTER_NAME, true);
    }

    @Override
    public List<Computer> getComputerListOrderedBy(long index, Long limit, DatabaseField computerField, boolean ascending) {
        Session session = sessionFactory.openSession();
        HibernateQuery<Computer> query = new HibernateQuery<>(session);
        query = query.select(qComputer)
                .from(qComputer)
                .leftJoin(qComputer.company, qCompany)
                .offset(index)
                .limit(limit);
        query = orderBy(query, computerField, ascending);

        if (query == null) {
            throw new IllegalStateException("The HibernateQuery must not be null!");
        }

        List<Computer> computerList = query.fetch();
        session.close();
        return computerList;
    }

    @Override
    public void updateComputer(Computer c) {
        try (Session session = sessionFactory.openSession()) {
            new HibernateUpdateClause(session, qComputer)
                    .where(qComputer.id.eq(c.getId()))
                    .set(qComputer.id, c.getId())
                    .set(qComputer.name, c.getName())
                    .set(qComputer.introduced, c.getIntroduced())
                    .set(qComputer.discontinued, c.getDiscontinued())
                    .set(qComputer.company.id, c.getCompany().getId())
                    .execute();
            session.flush();
        }
    }

    @Override
    public Long persistComputer(Computer c) {
        LOG.debug("Computer to persist: {}", c);
        try (Session session = sessionFactory.openSession()) {
            session.persist(c);
            session.flush();
        }
        return c.getId();
    }

    @Override
    public void deleteComputer(Long id) {
        deleteComputers(Collections.singletonList(id));
    }

    @Override
    public void deleteComputers(List<Long> idList) {
        Session session = sessionFactory.openSession();
        idList.forEach(id -> new HibernateDeleteClause(session, qComputer)
                .where(qComputer.id.eq(id))
                .execute());
        session.close();
    }

    @Override
    public void deleteComputersWhitCompanyId(Long companyId) {
        Session session = sessionFactory.openSession();
        new HibernateDeleteClause(session, qComputer)
                .where(qComputer.company.id.eq(companyId))
                .execute();
        session.close();
    }

    /**
     * @param query     the JPAQuery to set
     * @param field     the field to order by
     * @param ascending ascending or descending
     * @return the completed {@link HibernateQuery} if field exists, null otherwise
     */
    private HibernateQuery<Computer> orderBy(HibernateQuery<Computer> query, DatabaseField field, boolean ascending) {
        switch (field) {
            case COMPUTER_NAME:
                return query.orderBy(ascending ? qComputer.name.asc() : qComputer.name.desc());
            case INTRODUCED:
                return query.orderBy(ascending ? qComputer.introduced.asc() : qComputer.introduced.desc());
            case DISCONTINUED:
                return query.orderBy(ascending ? qComputer.discontinued.asc() : qComputer.discontinued.desc());
            case COMPANY_NAME:
                return query.orderBy(ascending ? qComputer.company.name.asc() : qComputer.company.name.desc());
            default:
                return null;
        }
    }
}

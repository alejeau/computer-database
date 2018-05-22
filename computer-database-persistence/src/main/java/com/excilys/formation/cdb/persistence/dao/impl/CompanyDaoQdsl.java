package com.excilys.formation.cdb.persistence.dao.impl;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.QCompany;
import com.excilys.formation.cdb.persistence.dao.CompanyDAO;
import com.querydsl.jpa.hibernate.HibernateDeleteClause;
import com.querydsl.jpa.hibernate.HibernateQuery;
import com.querydsl.jpa.hibernate.HibernateUpdateClause;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompanyDaoQdsl implements CompanyDAO {
    private static final Logger LOG = LoggerFactory.getLogger(CompanyDaoQdsl.class);

    private SessionFactory sessionFactory;
    private QCompany qCompany;

    @Autowired
    public CompanyDaoQdsl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.qCompany = QCompany.company;
    }

    @Override
    public Long getNumberOfCompanies() {
        LOG.debug("getNumberOfCompanies {}");
        Session session = sessionFactory.openSession();
        HibernateQuery<Company> query = new HibernateQuery<>(session);
        Long result = query.select(qCompany.id)
                .from(qCompany)
                .fetchCount();
        session.close();
        LOG.debug("Returning {}", result);
        return result;
    }

    @Override
    public Long getNumberOfCompaniesWithName(String name) {
        LOG.debug("getNumberOfCompaniesWithName {}");
        Session session = sessionFactory.openSession();
        HibernateQuery<Company> query = new HibernateQuery<>(session);
        Long result = query.select(qCompany.id)
                .from(qCompany)
                .where(qCompany.name.contains(name))
                .fetchCount();
        session.close();
        LOG.debug("Returning {}", result);
        return result;
    }

    @Override
    public Company getCompany(Long id) {
        LOG.debug("getCompany {}");
        Session session = sessionFactory.openSession();
        HibernateQuery<Company> query = new HibernateQuery<>(session);
        Company result = query.select(qCompany)
                .from(qCompany)
                .where(qCompany.id.eq(id))
                .fetchOne();
        session.close();
        LOG.debug("Returning {}", result);
        return result;
    }

    @Override
    public List<Company> getCompanyList() {
        LOG.debug("getCompanyList {}");
        Session session = sessionFactory.openSession();
        HibernateQuery<Company> query = new HibernateQuery<>(session);
        List<Company> companyList = query.select(qCompany)
                .from(qCompany)
                .orderBy(qCompany.name.asc())
                .fetch();
        session.close();
        LOG.debug("Returning list of size {}", companyList.size());
        return companyList;
    }

    @Override
    public List<Company> getCompanyList(Long index, Long limit) {
        LOG.debug("getNumberOfComputers {}");
        Session session = sessionFactory.openSession();
        HibernateQuery<Company> query = new HibernateQuery<>(session);
        List<Company> companyList = query.select(qCompany)
                .from(qCompany)
                .offset(index)
                .limit(limit)
                .orderBy(qCompany.name.asc())
                .fetch();
        session.close();
        LOG.debug("Returning list of size {}", companyList.size());
        return companyList;
    }

    @Override
    public List<Company> getCompaniesWithName(String name, Long index, Long limit) {
        LOG.debug("getComputersWithNameOrderedBy {}");
        Session session = sessionFactory.openSession();
        HibernateQuery<Computer> query = new HibernateQuery<>(session);

        List<Company> companyList = query.select(qCompany)
                .from(qCompany)
                .where(qCompany.name.contains(name))
                .offset(index)
                .limit(limit)
                .orderBy(qCompany.name.asc())
                .fetch();

        session.close();
        LOG.debug("Returning list of size {}", companyList.size());
        return companyList;
    }

    @Override
    public Long persistCompany(Company company) {
        LOG.debug("persistComputer {}");
        try (Session session = sessionFactory.openSession()) {
            session.save(company);
            session.flush();
        }
        LOG.debug("Returning id {}", company.getId());
        return company.getId();
    }

    @Override
    public void updateCompany(Company company) {
        LOG.debug("updateCompany: {}", company);
        try (Session session = sessionFactory.openSession()) {
            new HibernateUpdateClause(session, qCompany)
                    .where(qCompany.id.eq(company.getId()))
                    .set(qCompany.id, company.getId())
                    .set(qCompany.name, company.getName())
                    .execute();
            session.flush();
        }
    }

    @Override
    public void deleteCompany(Long id) {
        LOG.debug("deleteCompany {}");
        Session session = sessionFactory.openSession();
        new HibernateDeleteClause(session, qCompany).where(qCompany.id.eq(id)).execute();
        session.close();
    }
}

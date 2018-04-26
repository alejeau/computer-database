package com.excilys.formation.cdb.persistence.dao.impl;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.QCompany;
import com.excilys.formation.cdb.persistence.dao.CompanyDAO;
import com.querydsl.jpa.hibernate.HibernateDeleteClause;
import com.querydsl.jpa.hibernate.HibernateQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompanyDaoQdsl implements CompanyDAO {

    private SessionFactory sessionFactory;
    private QCompany qCompany;

    @Autowired
    public CompanyDaoQdsl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.qCompany = QCompany.company;
    }

    @Override
    public Long getNumberOfCompanies() {
        Session session = sessionFactory.openSession();
        HibernateQuery<Company> query = new HibernateQuery<>(session);
        Long result = query.select(qCompany.id)
                .from(qCompany)
                .fetchCount();
        session.close();
        return result;
    }

    @Override
    public Long getNumberOfCompaniesWithName(String name) {
        Session session = sessionFactory.openSession();
        HibernateQuery<Company> query = new HibernateQuery<>(session);
        Long result = query.select(qCompany.id)
                .from(qCompany)
                .where(qCompany.name.contains(name))
                .fetchCount();
        session.close();
        return result;
    }

    @Override
    public Company getCompany(Long id) {
        Session session = sessionFactory.openSession();
        HibernateQuery<Company> query = new HibernateQuery<>(session);
        Company result = query.select(qCompany)
                .from(qCompany)
                .where(qCompany.id.eq(id))
                .fetchOne();
        session.close();
        return result;
    }

    @Override
    public List<Company> getCompanies() {
        Session session = sessionFactory.openSession();
        HibernateQuery<Company> query = new HibernateQuery<>(session);
        List<Company> result = query.select(qCompany)
                .from(qCompany)
                .orderBy(qCompany.name.asc())
                .fetch();
        session.close();
        return result;
    }

    @Override
    public List<Company> getCompanies(Long index, Long limit) {
        Session session = sessionFactory.openSession();
        HibernateQuery<Company> query = new HibernateQuery<>(session);
        List<Company> result = query.select(qCompany)
                .from(qCompany)
                .offset(index)
                .limit(limit)
                .orderBy(qCompany.name.asc())
                .fetch();
        session.close();
        return result;
    }

    @Override
    public void deleteCompany(Long id) {
        Session session = sessionFactory.openSession();
        new HibernateDeleteClause(session, qCompany).where(qCompany.id.eq(id)).execute();
        session.close();
    }
}

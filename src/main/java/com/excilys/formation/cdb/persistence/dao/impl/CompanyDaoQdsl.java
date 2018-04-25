package com.excilys.formation.cdb.persistence.dao.impl;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.QCompany;
import com.excilys.formation.cdb.persistence.dao.CompanyDAO;
import com.querydsl.jpa.hibernate.HibernateQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
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
        throw new NotYetImplementedException();
//        return queryFactory.selectFrom(qCompany)
//                .where(qCompany.name.eq(name))
//                .fetchCount();
    }

    @Override
    public Company getCompany(Long id) {
        throw new NotYetImplementedException();
//        return queryFactory.selectFrom(qCompany)
//                .where(qCompany.id.eq(id))
//                .fetchOne();
    }

    @Override
    public List<Company> getCompanies() {
        throw new NotYetImplementedException();
//        return queryFactory.selectFrom(qCompany)
//                .fetch();
    }

    @Override
    public List<Company> getCompanies(Long index, Long limit) {
        throw new NotYetImplementedException();
//        return queryFactory.selectFrom(qCompany)
//                .offset(index)
//                .limit(limit)
//                .orderBy(qCompany.name.asc())
//                .fetch();
    }

    @Override
    public void deleteCompany(Long id) {
        throw new NotYetImplementedException();
//        queryFactory.delete(qCompany)
//                .where(qCompany.id.eq(id))
//                .execute();
    }
}

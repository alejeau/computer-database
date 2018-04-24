package com.excilys.formation.cdb.persistence.dao.impl;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.QCompany;
import com.excilys.formation.cdb.persistence.dao.CompanyDAO;
import com.querydsl.jpa.hibernate.HibernateQueryFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class CompanyDaoQdsl implements CompanyDAO {

    private HibernateQueryFactory queryFactory;
    private QCompany qCompany;

    @Autowired
    public CompanyDaoQdsl(SessionFactory sessionFactory) {
        this.queryFactory = new HibernateQueryFactory(sessionFactory.getCurrentSession());
        this.qCompany = QCompany.company;
    }

    @Override
    public Long getNumberOfCompanies() {
        return queryFactory.selectFrom(qCompany)
                .fetchCount();
    }

    @Override
    public Long getNumberOfCompaniesWithName(String name) {
        return queryFactory.selectFrom(qCompany)
                .where(qCompany.name.eq(name))
                .fetchCount();
    }

    @Override
    public Company getCompany(Long id) {
        return queryFactory.selectFrom(qCompany)
                .where(qCompany.id.eq(id))
                .fetchOne();
    }

    @Override
    public List<Company> getCompanies() {
        return queryFactory.selectFrom(qCompany)
                .fetch();
    }

    @Override
    public List<Company> getCompanies(Long index, Long limit) {
        return queryFactory.selectFrom(qCompany)
                .offset(index)
                .limit(limit)
                .orderBy(qCompany.name.asc())
                .fetch();
    }

    @Override
    public void deleteCompany(Long id) {
        queryFactory.delete(qCompany)
                .where(qCompany.id.eq(id))
                .execute();
    }
}

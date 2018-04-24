package com.excilys.formation.cdb.persistence.dao.impl;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.QCompany;
import com.excilys.formation.cdb.persistence.dao.CompanyDAO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CompanyDaoQdsl implements CompanyDAO {

    @PersistenceContext
    private EntityManager entityManager;
    private JPAQueryFactory queryFactory;
    private QCompany qCompany;

    public CompanyDaoQdsl() {
//        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(DAOUtils.EMF_NAME);
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
        this.queryFactory = new JPAQueryFactory(entityManager);
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

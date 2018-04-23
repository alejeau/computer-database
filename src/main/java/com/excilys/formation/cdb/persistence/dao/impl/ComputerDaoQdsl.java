package com.excilys.formation.cdb.persistence.dao.impl;

import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.QComputer;
import com.excilys.formation.cdb.persistence.DatabaseField;
import com.excilys.formation.cdb.persistence.dao.ComputerDAO;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Collections;
import java.util.List;

@Repository
public class ComputerDaoQdsl implements ComputerDAO {
    private JPAQueryFactory queryFactory;
    private QComputer qComputer;

    public ComputerDaoQdsl() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(DAOUtils.EMF_NAME);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        this.queryFactory = new JPAQueryFactory(entityManager);
        this.qComputer = QComputer.computer;
    }

    @Override
    public Long getNumberOfComputers() {
        return queryFactory.selectFrom(qComputer)
                .fetchCount();
    }

    @Override
    public Long getNumberOfComputersWithName(String name) {
//        String.format("SELECT COUNT(%s) FROM %s LEFT JOIN %s ON %s=%s.%s WHERE %s LIKE ? OR %s.%s LIKE ?;", COMPUTER_ID, COMPUTER, COMPANY, COMPUTER_COMPANY_ID, COMPANY, COMPANY_ID, COMPUTER_NAME, COMPANY, COMPANY_NAME);
        //TODO: Use a left join
        return queryFactory.selectFrom(qComputer)
//                .leftJoin(QCompany.company)
//                .on()
                .where(qComputer.name.eq(name))
                .where(qComputer.company.name.eq(name))
                .fetchCount();
    }

    @Override
    public Computer getComputer(Long id) {
        return queryFactory.selectFrom(qComputer)
                .where(qComputer.id.eq(id))
                .fetchOne();
    }

    @Override
    public List<Computer> getComputersWithName(String name, long index, Long limit) {
        return getComputersWithNameOrderedBy(name, index, limit, DatabaseField.COMPUTER_NAME, true);
    }

    @Override
    public List<Computer> getComputersWithNameOrderedBy(String name, long index, Long limit, DatabaseField computerField, boolean ascending) {
        //TODO: Use a left join
        JPAQuery<Computer> query = queryFactory.selectFrom(qComputer)
                .where(qComputer.name.eq(name))
                .where(qComputer.company.name.eq(name))
                .offset(index)
                .limit(limit);
        query = orderBy(query, computerField, ascending);
        return query.fetch();
    }

    @Override
    public List<Computer> getComputerList(long index, Long limit) {
        return getComputerListOrderedBy(index, limit, DatabaseField.COMPUTER_NAME, true);
    }

    @Override
    public List<Computer> getComputerListOrderedBy(long index, Long limit, DatabaseField computerField, boolean ascending) {
        JPAQuery<Computer> query = queryFactory.selectFrom(qComputer)
                .offset(index)
                .limit(limit);
        query = orderBy(query, computerField, ascending);
        return query.fetch();
    }

    @Override
    public void updateComputer(Computer c) {
        queryFactory.update(qComputer)
                .where(qComputer.id.eq(c.getId()))
                .set(qComputer.name, c.getName())
                .set(qComputer.introduced, c.getIntroduced())
                .set(qComputer.discontinued, c.getDiscontinued())
                .set(qComputer.company.id, c.getCompany().getId())
                .execute();
    }

    @Override
    public Long persistComputer(Computer c) {
       //TODO: persist Computer c
        throw new NotImplementedException();
    }

    @Override
    public void deleteComputer(Long id) {
        deleteComputers(Collections.singletonList(id));
    }

    @Override
    public void deleteComputers(List<Long> idList) {
        idList.forEach(id -> queryFactory
                .delete(qComputer)
                .where(qComputer.id.eq(id))
                .execute());
    }

    @Override
    public void deleteComputersWhitCompanyId(Long companyId) {
        queryFactory.delete(qComputer)
                .where(qComputer.company.id.eq(companyId))
                .execute();
    }

    /**
     *
     * @param query the JPAQuery to set
     * @param field the field to order by
     * @param ascending ascending or descending
     * @return the completed {@link JPAQuery<Computer>} if field exists, null otherwise
     */
    private JPAQuery<Computer> orderBy(JPAQuery<Computer> query, DatabaseField field, boolean ascending) {
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

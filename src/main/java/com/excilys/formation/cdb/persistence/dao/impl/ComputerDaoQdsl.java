//package com.excilys.formation.cdb.persistence.dao.impl;
//
//import com.excilys.formation.cdb.model.Computer;
//import com.excilys.formation.cdb.model.QComputer;
//import com.excilys.formation.cdb.persistence.DatabaseField;
//import com.excilys.formation.cdb.persistence.dao.ComputerDAO;
//import com.querydsl.jpa.hibernate.HibernateQuery;
//import com.querydsl.jpa.hibernate.HibernateQueryFactory;
//import org.hibernate.SessionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//import sun.reflect.generics.reflectiveObjects.NotImplementedException;
//
//import java.util.Collections;
//import java.util.List;
//
//@Repository
//public class ComputerDaoQdsl implements ComputerDAO {
//
//    private HibernateQueryFactory queryFactory;
//    private QComputer qComputer;
//
//    @Autowired
//    public ComputerDaoQdsl(SessionFactory sessionFactory) {
//        this.queryFactory = new HibernateQueryFactory(sessionFactory.getCurrentSession());
//        this.qComputer = QComputer.computer;
//    }
//
//    @Override
//    public Long getNumberOfComputers() {
//        return queryFactory
//                .select(qComputer.id)
//                .from(qComputer)
//                .fetchCount();
//    }
//
//    @Override
//    public Long getNumberOfComputersWithName(String name) {
////        String.format("SELECT COUNT(%s) FROM %s LEFT JOIN %s ON %s=%s.%s WHERE %s LIKE ? OR %s.%s LIKE ?;", COMPUTER_ID, COMPUTER, COMPANY, COMPUTER_COMPANY_ID, COMPANY, COMPANY_ID, COMPUTER_NAME, COMPANY, COMPANY_NAME);
//        //TODO: Use a left join
//        return queryFactory.selectFrom(qComputer)
////                .leftJoin(QCompany.company)
////                .on()
//                .where(qComputer.name.eq(name))
//                .where(qComputer.company.name.eq(name))
//                .fetchCount();
//    }
//
//    @Override
//    public Computer getComputer(Long id) {
//        return queryFactory.selectFrom(qComputer)
//                .where(qComputer.id.eq(id))
//                .fetchOne();
//    }
//
//    @Override
//    public List<Computer> getComputersWithName(String name, long index, Long limit) {
//        return getComputersWithNameOrderedBy(name, index, limit, DatabaseField.COMPUTER_NAME, true);
//    }
//
//    @Override
//    public List<Computer> getComputersWithNameOrderedBy(String name, long index, Long limit, DatabaseField computerField, boolean ascending) {
//        //TODO: Use a left join
//        HibernateQuery<Computer> query = queryFactory.selectFrom(qComputer)
//                .where(qComputer.name.eq(name))
//                .where(qComputer.company.name.eq(name))
//                .offset(index)
//                .limit(limit);
//        query = orderBy(query, computerField, ascending);
//        if (query != null) {
//            return query.fetch();
//        } else {
//            throw new IllegalStateException("The HibernateQuery must not be null!");
//        }
//    }
//
//    @Override
//    public List<Computer> getComputerList(long index, Long limit) {
//        return getComputerListOrderedBy(index, limit, DatabaseField.COMPUTER_NAME, true);
//    }
//
//    @Override
//    public List<Computer> getComputerListOrderedBy(long index, Long limit, DatabaseField computerField, boolean ascending) {
//        HibernateQuery<Computer> query = queryFactory.selectFrom(qComputer)
//                .offset(index)
//                .limit(limit);
//        query = orderBy(query, computerField, ascending);
//        if (query != null) {
//            return query.fetch();
//        } else {
//            throw new IllegalStateException("The HibernateQuery must not be null!");
//        }
//    }
//
//    @Override
//    public void updateComputer(Computer c) {
//        queryFactory.update(qComputer)
//                .where(qComputer.id.eq(c.getId()))
//                .set(qComputer.name, c.getName())
//                .set(qComputer.introduced, c.getIntroduced())
//                .set(qComputer.discontinued, c.getDiscontinued())
//                .set(qComputer.company.id, c.getCompany().getId())
//                .execute();
//    }
//
//    @Override
//    public Long persistComputer(Computer c) {
//        //TODO: persist Computer c
//        throw new NotImplementedException();
//    }
//
//    @Override
//    public void deleteComputer(Long id) {
//        deleteComputers(Collections.singletonList(id));
//    }
//
//    @Override
//    public void deleteComputers(List<Long> idList) {
//        idList.forEach(id -> queryFactory
//                .delete(qComputer)
//                .where(qComputer.id.eq(id))
//                .execute());
//    }
//
//    @Override
//    public void deleteComputersWhitCompanyId(Long companyId) {
//        queryFactory.delete(qComputer)
//                .where(qComputer.company.id.eq(companyId))
//                .execute();
//    }
//
//    /**
//     * @param query     the HibernateQuery to set
//     * @param field     the field to order by
//     * @param ascending ascending or descending
//     * @return the completed {@link HibernateQuery<Computer>} if field exists, null otherwise
//     */
//    private HibernateQuery<Computer> orderBy(HibernateQuery<Computer> query, DatabaseField field, boolean ascending) {
//        switch (field) {
//            case COMPUTER_NAME:
//                return query.orderBy(ascending ? qComputer.name.asc() : qComputer.name.desc());
//            case INTRODUCED:
//                return query.orderBy(ascending ? qComputer.introduced.asc() : qComputer.introduced.desc());
//            case DISCONTINUED:
//                return query.orderBy(ascending ? qComputer.discontinued.asc() : qComputer.discontinued.desc());
//            case COMPANY_NAME:
//                return query.orderBy(ascending ? qComputer.company.name.asc() : qComputer.company.name.desc());
//            default:
//                return null;
//        }
//    }
//}

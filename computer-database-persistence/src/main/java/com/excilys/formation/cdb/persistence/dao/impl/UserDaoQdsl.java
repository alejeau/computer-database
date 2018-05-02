package com.excilys.formation.cdb.persistence.dao.impl;

import com.excilys.formation.cdb.login.QUser;
import com.excilys.formation.cdb.login.User;
import com.excilys.formation.cdb.persistence.dao.UserDAO;
import com.querydsl.jpa.hibernate.HibernateDeleteClause;
import com.querydsl.jpa.hibernate.HibernateQuery;
import com.querydsl.jpa.hibernate.HibernateUpdateClause;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoQdsl implements UserDAO {
    private static final Logger LOG = LoggerFactory.getLogger(UserDaoQdsl.class);

    private SessionFactory sessionFactory;
    private QUser qUser;

    @Autowired
    public UserDaoQdsl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.qUser = QUser.user;
    }

    @Override
    public void createUser(User user) {
        LOG.debug("createUser {}");
        try (Session session = sessionFactory.openSession()) {
            session.save(user);
            session.flush();
        }
    }

    @Override
    public User getUserWithName(String username) {
        LOG.debug("getUserWithName {}");
        User user;
        try (Session session = sessionFactory.openSession()) {
            user = new HibernateQuery<User>(session).select(qUser)
                    .from(qUser)
                    .where(qUser.username.eq(username))
                    .fetchOne();
        }
        LOG.debug("Returning user {}", user);
        return user;
    }

    @Override
    public void updateUser(User user) {
        LOG.debug("updateUser {}");
        try (Session session = sessionFactory.openSession()) {
            new HibernateUpdateClause(session, qUser)
                    .where(qUser.username.eq(user.getUsername()))
                    .set(qUser.password, user.getPassword())
                    .set(qUser.role, user.getRole())
                    .execute();
            session.flush();
        }
    }

    @Override
    public void deleteUser(String username) {
        LOG.debug("deleteUser {}");
        try (Session session = sessionFactory.openSession()) {
            new HibernateDeleteClause(session, qUser)
                    .where(qUser.username.eq(username))
                    .execute();
        }
    }
}

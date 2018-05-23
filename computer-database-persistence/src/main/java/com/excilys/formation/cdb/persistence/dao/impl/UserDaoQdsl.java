package com.excilys.formation.cdb.persistence.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.excilys.formation.cdb.model.QUser;
import com.excilys.formation.cdb.model.User;
import com.querydsl.jpa.hibernate.HibernateQuery;

@Repository
@EnableTransactionManagement
public class UserDaoQdsl {

	private static final Logger LOG = LoggerFactory.getLogger(ComputerDaoQdsl.class);
	private SessionFactory sessionFactory;
	private QUser qUser;

	@Autowired
	public UserDaoQdsl (SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		this.qUser = QUser.user;
	}

	public User getUserByUsername(String username) {
		 LOG.debug("getUserByUsername {}");
	        Session session = sessionFactory.openSession();
	        HibernateQuery<User> query = new HibernateQuery<>(session);
	        User user = (User) query.select()
	                .from(qUser)
	                .where(qUser.username.eq(username)).fetch();
	        session.close();
	        LOG.debug("Returning {}", user);
	        return user;
	}
}

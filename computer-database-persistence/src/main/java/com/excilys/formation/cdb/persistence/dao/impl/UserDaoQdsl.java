package com.excilys.formation.cdb.persistence.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.QUserInfo;
import com.excilys.formation.cdb.model.UserInfo;
import com.querydsl.jpa.hibernate.HibernateQuery;

@Repository
@EnableTransactionManagement
public class UserDaoQdsl {

	private static final Logger LOG = LoggerFactory.getLogger(ComputerDaoQdsl.class);
	private SessionFactory sessionFactory;
	private QUserInfo qUserInfo;

	@Autowired
	public UserDaoQdsl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		this.qUserInfo = QUserInfo.userInfo;
	}

	public UserInfo getUserByUsername(String username) {
		LOG.debug("getUserByUsername {}", username);
		Session session = sessionFactory.openSession();
		HibernateQuery<UserInfo> query = new HibernateQuery<>(session);
		UserInfo user = query.from(qUserInfo).where(qUserInfo.username.eq(username)).fetchOne();
		session.close();
		LOG.debug("Returning {}", user);
		return user;
	}

	public void persistUser (String username, String password) {
		LOG.debug("addUser {}, {}", username, password);
		UserInfo user = new UserInfo();
		user.setUsername(username);
		user.setPassword(password);
		user.setRole("USER");
		try (Session session = sessionFactory.openSession()) {
			session.save(user);
			session.flush();
		}
	}

}

package com.excilys.formation.cdb.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.formation.cdb.model.UserInfo;
import com.excilys.formation.cdb.persistence.dao.impl.UserDaoQdsl;
import com.excilys.formation.cdb.user.User;

@Service
public class SimpleAuthenticationService {

	private UserDaoQdsl userDAO;
	
	@Autowired
	public SimpleAuthenticationService (UserDaoQdsl userDAO) {
		this.userDAO = userDAO;
		
	}
	
	Map<String, User> users = new HashMap<>();

	public Optional<String> login(String username, String password) {
		UserInfo userInfo = userDAO.getUserByUsername(username);
		if (password.equals(userInfo.getPassword())) {
			String token = UUID.randomUUID().toString();
			User user = new User(token, username, password); 
			users.put(token, user);
			return Optional.of(token);	
		}
		return Optional.empty();
	}

	public Optional<User> findByToken(String token) {
		return Optional.ofNullable(users.get(token));
	}

	public void logout(User user) {
		users.remove(user.getId());
	}
}
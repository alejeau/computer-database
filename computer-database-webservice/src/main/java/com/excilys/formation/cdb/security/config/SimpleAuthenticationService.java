package com.excilys.formation.cdb.security.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.excilys.formation.cdb.model.UserInfo;
import com.excilys.formation.cdb.persistence.dao.impl.UserDaoQdsl;

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
			User user = new User(token, username, password, userInfo.getRole()); 
			users.put(token, user);
			return Optional.of(token);	
		}
		return Optional.empty();
	}

	public Optional<User> findByToken(String token) {
		return Optional.ofNullable(users.get(token));
	}
	
	public String getRole(String token) {
			User user = users.get(token);
		if (user == null) {
			return null;
		}
		return user.getRole();
	}

	public void logout(String token) {
		users.remove(token);
	}
}
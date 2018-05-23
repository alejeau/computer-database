package com.excilys.formation.cdb.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.excilys.formation.cdb.user.User;

@Service
public class SimpleAuthenticationService {

	// BD ??
	Map<String, User> users = new HashMap<>();

	public Optional<String> login(String username, String password) {
		final String token = UUID.randomUUID().toString();
		final User user = new User(token, username, password); 
		users.put(token, user);
		return Optional.of(token);
	}

	public Optional<User> findByToken(String token) {
		return Optional.ofNullable(users.get(token));
	}

	public void logout(User user) {
		users.remove(user.getId());
	}
}
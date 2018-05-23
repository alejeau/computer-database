package com.excilys.formation.cdb.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
class TokenAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	SimpleAuthenticationService auth;
	
	@Autowired
	public TokenAuthenticationProvider(SimpleAuthenticationService authService) {
		this.auth = authService;
	}
	@Override
	protected void additionalAuthenticationChecks(UserDetails d, UsernamePasswordAuthenticationToken auth) {
		// Nothing to do
	}

	@Override
	protected UserDetails retrieveUser(String username,
			 UsernamePasswordAuthenticationToken authentication) {
		final Object token = authentication.getCredentials();
		return Optional.ofNullable(token).map(String::valueOf).flatMap(auth::findByToken).orElseThrow(
				() -> new UsernameNotFoundException("Cannot find user with authentication token=" + token));

	}
}

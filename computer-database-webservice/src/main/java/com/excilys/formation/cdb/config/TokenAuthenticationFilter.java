package com.excilys.formation.cdb.config;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private static final String AUTHORIZATION = "X-AUTH-TOKEN";

	private static Logger logger = LoggerFactory.getLogger(TokenAuthenticationFilter.class);
	
	TokenAuthenticationFilter(RequestMatcher requiresAuth) {
		super(requiresAuth);
	}

	@Override
	public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response) {
		String token = Optional.ofNullable(request.getHeader(AUTHORIZATION))
				.orElseThrow(() -> new BadCredentialsException("Missing Authentication Token"));
		Authentication auth = new UsernamePasswordAuthenticationToken(token, token);
		logger.error("Authentication Manager \n \n {} \n \n", getAuthenticationManager() == null);
		return getAuthenticationManager().authenticate(auth);
		
		/*
		 * final String token = Optional.ofNullable(param) .map(value ->
		 * removeStart(value, BEARER)) .map(String::trim) .orElseThrow(() -> new
		 * BadCredentialsException("Missing Authentication Token"));
		 */

	}

	@Override
	protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response,
			final FilterChain chain, final Authentication authResult) throws IOException, ServletException {
		super.successfulAuthentication(request, response, chain, authResult);
		chain.doFilter(request, response);
	}

}

package com.excilys.formation.cdb.rest.impl;

import java.util.Optional;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.formation.cdb.model.UserInfo;
import com.excilys.formation.cdb.model.constants.Paths;
import com.excilys.formation.cdb.security.config.SimpleAuthenticationService;

@RestController
@RequestMapping(Paths.REST_LOGOUT)
public class LogoutRestController {

	private SimpleAuthenticationService authentication;

	@Autowired
	public LogoutRestController(SimpleAuthenticationService authService) {
		this.authentication = authService;
	} 


	@PostMapping
	public void logout(HttpServlet request, @RequestBody String token) {
		authentication.logout(token);

	}
}
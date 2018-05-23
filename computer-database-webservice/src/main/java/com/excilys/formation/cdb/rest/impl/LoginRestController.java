package com.excilys.formation.cdb.rest.impl;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.formation.cdb.config.SimpleAuthenticationService;
import com.excilys.formation.cdb.model.constants.Paths;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(Paths.REST_LOGIN)
public class LoginRestController {

	private static Logger logger = LoggerFactory.getLogger(LoginRestController.class);
	
	SimpleAuthenticationService authentication;

	@Autowired
	public LoginRestController(SimpleAuthenticationService authService) {
		this.authentication = authService;
	}

	@PostMapping
	public String login(HttpServletRequest request, @RequestParam("username") String username,
			@RequestParam("password") String password) {
		logger.debug("\n-------\n \n \n \nLOGIN POST CONTROLLER");
		return authentication.login(username, password)
				.orElseThrow(() -> new RuntimeException("invalid login and/or password"));
	}
}
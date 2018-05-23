package com.excilys.formation.cdb.rest.impl;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.excilys.formation.cdb.config.SimpleAuthenticationService;
import com.excilys.formation.cdb.model.UserInfo;
import com.excilys.formation.cdb.model.constants.Paths;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(Paths.REST_LOGIN)
public class LoginRestController {

	
	SimpleAuthenticationService authentication;

	@Autowired
	public LoginRestController(SimpleAuthenticationService authService) {
		this.authentication = authService;
	}

	@PostMapping
	public String login(HttpServletRequest request, @RequestBody UserInfo user) {
		return authentication.login(user.getUsername(), user.getPassword())
				.orElseThrow(() -> new RuntimeException("invalid login and/or password"));
	}
}
package com.excilys.formation.cdb.rest.impl;

import com.excilys.formation.cdb.model.constants.Paths;
import com.excilys.formation.cdb.security.config.SimpleAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;

@RestController
@CrossOrigin(origins = "*")
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
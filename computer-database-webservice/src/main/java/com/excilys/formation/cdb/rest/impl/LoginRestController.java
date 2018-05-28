package com.excilys.formation.cdb.rest.impl;

import com.excilys.formation.cdb.model.UserInfo;
import com.excilys.formation.cdb.model.constants.Paths;
import com.excilys.formation.cdb.security.config.SimpleAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(Paths.REST_LOGIN)
public class LoginRestController {

    private SimpleAuthenticationService authentication;

    @Autowired
    public LoginRestController(SimpleAuthenticationService authService) {
        this.authentication = authService;
    }

    @PostMapping
    public String login(HttpServletResponse response, @RequestBody UserInfo user) {
        Optional<String> authResultOpt = authentication.login(user.getUsername(), user.getPassword());
        if (!authResultOpt.isPresent()) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return null;
        }
        return authResultOpt.get();
    }


}
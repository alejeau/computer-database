package com.excilys.formation.cdb.controllers;

import com.excilys.formation.cdb.model.constants.Paths;
import com.excilys.formation.cdb.model.constants.Views;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Map;

@Controller
public class AuthController {
    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    @GetMapping(Paths.LOCAL_PATH_403)
    public ModelAndView get403() {
        return new ModelAndView(Views.ERROR_403);
    }

    @GetMapping(Paths.LOCAL_PATH_LOGOUT)
    public ModelAndView getLogout(@RequestParam Map<String, String> params, HttpServletRequest request, HttpServletResponse response) {
        LOG.debug("getLogout");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return getLogin(params);
    }

    @GetMapping(Paths.LOCAL_PATH_LOGIN)
    public ModelAndView getLogin(@RequestParam Map<String, String> params) {
        LOG.debug("get");
        ModelAndView modelAndView = new ModelAndView(Views.LOGIN);

        Locale currentLocale = LocaleContextHolder.getLocale();
        modelAndView.addObject("locale", currentLocale);

        modelAndView.addObject("error", params.get("error"));
        modelAndView.addObject("logout", params.get("logout"));

        return modelAndView;
    }
}

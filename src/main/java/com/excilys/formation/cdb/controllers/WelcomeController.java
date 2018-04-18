package com.excilys.formation.cdb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static com.excilys.formation.cdb.controllers.constants.Views.INDEX;

@Controller
@RequestMapping("/")
public class WelcomeController {

    public WelcomeController() {
    }

    @GetMapping
    public ModelAndView get() {
        return new ModelAndView(INDEX);
    }
}

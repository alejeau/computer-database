package com.excilys.formation.cdb.controllers;

import com.excilys.formation.cdb.model.constants.Views;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class WelcomeController {

    public WelcomeController() {
    }

    @GetMapping
    public ModelAndView get() {
        return new ModelAndView(Views.INDEX);
    }
}

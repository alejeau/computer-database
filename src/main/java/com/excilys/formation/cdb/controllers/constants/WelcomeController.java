package com.excilys.formation.cdb.controllers.constants;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import static com.excilys.formation.cdb.controllers.constants.Views.INDEX;

@Controller
@RequestMapping("/")
public class WelcomeController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView get() {
        return new ModelAndView(INDEX);
    }
}

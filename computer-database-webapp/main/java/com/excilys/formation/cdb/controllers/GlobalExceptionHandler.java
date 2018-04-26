package com.excilys.formation.cdb.controllers;

import com.excilys.formation.cdb.controllers.constants.Views;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String EXCEPTION_ATTRIBUTE_NAME = "exception";

    //TODO: 403 Error to handle when Spring Security is added

    /**
     * Handles 404 HTML errors
     *
     * @param exception the 404: Page not found exception
     * @return ModelAndView of the error Views.ERROR_404 and the exception
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handle(NoHandlerFoundException exception) {
        ModelAndView model = new ModelAndView(Views.ERROR_404);
        model.addObject(EXCEPTION_ATTRIBUTE_NAME, exception);
        return model;
    }

    /**
     * Handles 500 HTML errors
     *
     * @param exception the any runtime exception
     * @return ModelAndView of the error Views.ERROR_500 and the exception
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView handle(Exception exception) {
        ModelAndView model = new ModelAndView(Views.ERROR_500);
        model.addObject(EXCEPTION_ATTRIBUTE_NAME, exception);
        return model;
    }
}

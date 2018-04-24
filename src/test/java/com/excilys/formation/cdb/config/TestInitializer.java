package com.excilys.formation.cdb.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class TestInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
//        return new Class<?>[]{WebMvcConfig.class};
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{HibernatePersistenceConfigTest.class};
    }

//    @Override
//    protected DispatcherServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
//        final DispatcherServlet servlet = (DispatcherServlet) super.createDispatcherServlet(servletAppContext);
//        servlet.setThrowExceptionIfNoHandlerFound(true);
//        return servlet;
//    }

    @Override
    protected String[] getServletMappings() {
//        return new String[]{"/"};
        return null;
    }
}
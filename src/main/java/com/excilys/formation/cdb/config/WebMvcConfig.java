package com.excilys.formation.cdb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.annotation.PostConstruct;

@EnableWebMvc
@ComponentScan(basePackages = {
        "com.excilys.formation.cdb.persistence.dao.impl",
        "com.excilys.formation.cdb.service.impl",
        "com.excilys.formation.cdb.paginator.pager",
        "com.excilys.formation.cdb.mapper.request",
        "com.excilys.formation.cdb.controllers"
})
@Configuration
@ContextConfiguration(classes = WebAppConfig.class)
public class WebMvcConfig implements WebMvcConfigurer {

    private static final int CACHE_PERIOD = 31556926; // one year

    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    @Autowired
    public WebMvcConfig(RequestMappingHandlerAdapter requestMappingHandlerAdapter) {
        this.requestMappingHandlerAdapter = requestMappingHandlerAdapter;
    }

    @PostConstruct
    public void init() {
        requestMappingHandlerAdapter.setIgnoreDefaultModelOnRedirect(true);
    }

    @Bean
    public ViewResolver viewResolver() {
        // Example: the 'info' view logical name is mapped to the file '/WEB-INF/jsp/info.jsp'
        InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setViewClass(JstlView.class);
        bean.setPrefix("/WEB-INF/JSPs/");
        bean.setSuffix(".jsp");
        return bean;
    }

//    @Bean(name = "messageSource")
//    public ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource() {
//        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
//        messageSource.setBasenames("classpath:com/javaetmoi/sample/web/messages");
//        messageSource.setDefaultEncoding("UTF-8");
//        return messageSource;
//    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Static ressources from both WEB-INF and webjars
        registry
                .addResourceHandler("/resources/**")
                .addResourceLocations("/resources/")
                .setCachePeriod(CACHE_PERIOD);
    }
//
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/about"); // the about page did not required any controller
//    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        // Serving static files using the Servlet container's default Servlet.
        configurer.enable();
    }
//
//    @Override
//    public void addFormatters(FormatterRegistry formatterRegistry) {
//        // add your custom formatters
//    }
}
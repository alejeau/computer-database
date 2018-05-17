package com.excilys.formation.cdb.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = {
        "com.excilys.formation.cdb.config",
        "com.excilys.formation.cdb.persistence",
        "com.excilys.formation.cdb.persistence.dao.impl",
        "com.excilys.formation.cdb.service.impl",
        "com.excilys.formation.cdb.service.validators",
        "com.excilys.formation.cdb.service.paginator.pager",
        "com.excilys.formation.cdb.mapper.request",
        "com.excilys.formation.cdb.rest"
})
public class WebMvcRestConfig implements WebMvcConfigurer {
}
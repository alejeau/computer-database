package com.excilys.formation.cdb.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:/properties/db-test.properties")
@ComponentScan(basePackages = {
        "com.excilys.formation.cdb.utils",
        "com.excilys.formation.cdb.persistence.dao.impl",
        "com.excilys.formation.cdb.service.impl",
        "com.excilys.formation.cdb.paginator.pager",
        "com.excilys.formation.cdb.mapper.request"
})
public class TestConfig {

    @Value("${hsqldb.driverClassName}")
    private String driverClassName;

    @Value("${hsqldb.url}")
    private String url;

    @Value("${hsqldb.username}")
    private String username;

    @Value("${hsqldb.password}")
    private String password;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource(url, username, password);
        driverManagerDataSource.setDriverClassName(driverClassName);
        return driverManagerDataSource;
    }

    @Bean
    public DataSourceTransactionManager txManager() {
        return new DataSourceTransactionManager(dataSource());
    }
}
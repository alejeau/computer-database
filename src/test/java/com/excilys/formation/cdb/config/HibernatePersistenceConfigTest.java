package com.excilys.formation.cdb.config;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Properties;

@Configuration
@PropertySource("classpath:/properties/db-test.properties")
@ComponentScan("com.excilys.formation.cdb")
@EnableTransactionManagement
public class HibernatePersistenceConfigTest {
    private Environment environment;

    @Autowired
    public HibernatePersistenceConfigTest(Environment environment) {
        this.environment = environment;
    }

    @Bean("SessionFactory")
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("com.excilys.formation.cdb.model");
        sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }

    @Bean
    public DataSource dataSource() {
        final String DRIVER_CLASS_NAME = "hsqldb.driverClassName";
        final String URL = "hsqldb.url";
        final String USERNAME = "hsqldb.username";
        final String PASSWORD = "hsqldb.password";

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(environment.getProperty(DRIVER_CLASS_NAME));
        dataSource.setUrl(environment.getProperty(URL));
        dataSource.setUsername(environment.getProperty(USERNAME));
        dataSource.setPassword(environment.getProperty(PASSWORD));
        return dataSource;
    }

    @Bean
    @Autowired
    public PlatformTransactionManager hibernateTransactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    private Properties hibernateProperties() {
        return new Properties() {
            // Using Environment to get the properties because they are otherwise equals to ${value}
            {
                final String HBM2DDL = "hibernate.hbm2ddl.auto";
                final String DIALECT = "hibernate.dialect";
                final String SHOW_SQL = "hibernate.show_sql";

                Arrays.asList(HBM2DDL, DIALECT, SHOW_SQL)
                        .forEach(p -> setProperty(p, environment.getProperty(p)));
            }
        };
    }
}

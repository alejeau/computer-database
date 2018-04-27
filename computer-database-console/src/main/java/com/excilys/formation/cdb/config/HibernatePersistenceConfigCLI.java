package com.excilys.formation.cdb.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:/properties/db.properties")
@ComponentScan(basePackages = {
        "com.excilys.formation.cdb.persistence",
        "com.excilys.formation.cdb.persistence.dao.impl",
        "com.excilys.formation.cdb.service.impl",
        "com.excilys.formation.cdb.service.validators",
        "com.excilys.formation.cdb.service.paginator.pager",
        "com.excilys.formation.cdb.mapper.request",
        "com.excilys.formation.cdb.ui"
})
public class HibernatePersistenceConfigCLI extends ParamsFactory {
    private Environment environment;

    @Autowired
    public HibernatePersistenceConfigCLI(Environment environment) {
        this.environment = environment;
    }

    @Bean("SessionFactory")
    public LocalSessionFactoryBean sessionFactory() {
        return createSessionFactory(dataSource(), hibernateProperties());
    }

    @Bean
    public DataSource dataSource() {
        return createDataSource(
                environment.getProperty("jdbc.driverClassName"),
                environment.getProperty("jdbc.jdbcUrl"),
                environment.getProperty("jdbc.username"),
                environment.getProperty("jdbc.password")
        );
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
                Arrays.asList(
                        "hibernate.hbm2ddl.auto",
                        "hibernate.dialect",
                        "hibernate.show_sql"
                ).forEach(p -> setProperty(p, environment.getProperty(p)));
            }
        };
    }
}

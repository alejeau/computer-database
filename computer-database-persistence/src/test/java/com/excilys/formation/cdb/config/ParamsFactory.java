package com.excilys.formation.cdb.config;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

public class ParamsFactory {

    protected DataSource createDataSource(String driverClassName, String url, String username, String password) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    protected LocalSessionFactoryBean createSessionFactory(DataSource dataSource, Properties hibernateProperties) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan("com.excilys.formation.cdb.model");
        sessionFactory.setHibernateProperties(hibernateProperties);

        return sessionFactory;
    }
}

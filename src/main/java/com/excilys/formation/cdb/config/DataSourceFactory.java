package com.excilys.formation.cdb.config;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import javax.sql.DataSource;

public class DataSourceFactory {
    protected DataSource dataSource(String driverClassName, String url, String username, String password) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }
}

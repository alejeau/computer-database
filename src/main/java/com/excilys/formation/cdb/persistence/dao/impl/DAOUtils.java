package com.excilys.formation.cdb.persistence.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.excilys.formation.cdb.persistence.dao.impl.DbFields.COMPUTER;
import static com.excilys.formation.cdb.persistence.dao.impl.DbFields.COMPUTER_COMPANY_ID;
import static com.excilys.formation.cdb.persistence.dao.impl.DbFields.COMPUTER_DISCONTINUED;
import static com.excilys.formation.cdb.persistence.dao.impl.DbFields.COMPUTER_INTRODUCED;
import static com.excilys.formation.cdb.persistence.dao.impl.DbFields.COMPUTER_NAME;

@Component
public class DAOUtils {
    public static final String EMF_NAME = "com.excilys.formation.cdb";
    public static final String INSERT_COMPUTER = "insert into computer (computer_company_id, computer_discontinued, computer_introduced, computer_name) values (?, ?, ?, ?)";



}

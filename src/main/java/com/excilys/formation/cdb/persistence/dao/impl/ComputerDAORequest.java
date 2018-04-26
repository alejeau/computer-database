package com.excilys.formation.cdb.persistence.dao.impl;

import org.springframework.stereotype.Component;

import static com.excilys.formation.cdb.persistence.dao.impl.DbFields.COMPANY;
import static com.excilys.formation.cdb.persistence.dao.impl.DbFields.COMPANY_ID;
import static com.excilys.formation.cdb.persistence.dao.impl.DbFields.COMPANY_NAME;
import static com.excilys.formation.cdb.persistence.dao.impl.DbFields.COMPUTER;
import static com.excilys.formation.cdb.persistence.dao.impl.DbFields.COMPUTER_AND_COMPANY_STAR;
import static com.excilys.formation.cdb.persistence.dao.impl.DbFields.COMPUTER_COMPANY_ID;
import static com.excilys.formation.cdb.persistence.dao.impl.DbFields.COMPUTER_DISCONTINUED;
import static com.excilys.formation.cdb.persistence.dao.impl.DbFields.COMPUTER_ID;
import static com.excilys.formation.cdb.persistence.dao.impl.DbFields.COMPUTER_INTRODUCED;
import static com.excilys.formation.cdb.persistence.dao.impl.DbFields.COMPUTER_NAME;

class ComputerDAORequest {
    static final String ORDER_FIELD = "{orderField}";
    static final String DIRECTION = "{direction}";
    static final String NUMBER_OF_COMPUTERS = String.format("SELECT COUNT(%s) FROM %s;", COMPUTER_ID, COMPUTER);
    static final String NUMBER_OF_COMPUTERS_WITH_NAME_OR_COMPANY_NAME = String.format("SELECT COUNT(%s) FROM %s LEFT JOIN %s ON %s=%s.%s WHERE %s LIKE ? OR %s.%s LIKE ?;", COMPUTER_ID, COMPUTER, COMPANY, COMPUTER_COMPANY_ID, COMPANY, COMPANY_ID, COMPUTER_NAME, COMPANY, COMPANY_NAME);
    static final String SELECT_COMPUTER_BY_ID = String.format("SELECT %s FROM %s LEFT JOIN %s ON %s=%s.%s WHERE %s=?;", COMPUTER_AND_COMPANY_STAR, COMPUTER, COMPANY, COMPUTER_COMPANY_ID, COMPANY, COMPANY_ID, COMPUTER_ID);
    static final String SELECT_COMPUTER_BY_NAME_OR_COMPANY_NAME_ORDERED_BY = String.format("SELECT %s FROM %s LEFT JOIN %s ON %s=%s.%s WHERE %s LIKE ? OR %s.%s LIKE ? ORDER BY %s %s LIMIT ?, ?;", COMPUTER_AND_COMPANY_STAR, COMPUTER, COMPANY, COMPUTER_COMPANY_ID, COMPANY, COMPANY_ID, COMPUTER_NAME, COMPANY, COMPANY_NAME, ORDER_FIELD, DIRECTION);
    static final String SELECT_ALL_COMPUTERS_ORDERED_BY = String.format("SELECT %s FROM %s LEFT JOIN %s ON %s=%s.%s ORDER BY %s %s LIMIT ?, ?;", COMPUTER_AND_COMPANY_STAR, COMPUTER, COMPANY, COMPUTER_COMPANY_ID, COMPANY, COMPANY_ID, ORDER_FIELD, DIRECTION);
    static final String INSERT_COMPUTER = String.format("INSERT INTO %s (%s, %s, %s, %s) values (?, ?, ?, ?);", COMPUTER, COMPUTER_NAME, COMPUTER_INTRODUCED, COMPUTER_DISCONTINUED, COMPUTER_COMPANY_ID);
    static final String UPDATE_COMPUTER = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?;", COMPUTER, COMPUTER_NAME, COMPUTER_INTRODUCED, COMPUTER_DISCONTINUED, COMPUTER_COMPANY_ID, COMPUTER_ID);
    static final String DELETE_COMPUTER = String.format("DELETE from %s WHERE %s = ?;", COMPUTER, COMPUTER_ID);
    static final String DELETE_COMPUTER_WITH_COMPANY_ID = String.format("DELETE FROM %s WHERE %s=?", COMPUTER, COMPUTER_COMPANY_ID);

    private ComputerDAORequest() {
    }
}

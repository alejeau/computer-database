package com.excilys.formation.cdb.persistence.dao.impl;

import static com.excilys.formation.cdb.persistence.dao.impl.DbFields.COMPANY;
import static com.excilys.formation.cdb.persistence.dao.impl.DbFields.COMPANY_ID;
import static com.excilys.formation.cdb.persistence.dao.impl.DbFields.COMPANY_NAME;
import static com.excilys.formation.cdb.persistence.dao.impl.DbFields.COMPANY_STAR;
import static com.excilys.formation.cdb.persistence.dao.impl.DbFields.COMPUTER;
import static com.excilys.formation.cdb.persistence.dao.impl.DbFields.COMPUTER_COMPANY_ID;
import static com.excilys.formation.cdb.persistence.dao.impl.DbFields.COMPUTER_ID;

class CompanyDAORequest {
    private CompanyDAORequest() {
    }

    static final String NUMBER_OF_COMPANIES = String.format("SELECT COUNT(%s) FROM %s;", DbFields.COMPANY_ID, COMPANY);
    static final String NUMBER_OF_COMPANIES_WITH_NAME = String.format("SELECT COUNT(%s) FROM %s WHERE %s LIKE ?;", COMPANY_ID, COMPANY, COMPANY_NAME);
    static final String COMPANY_BY_ID = String.format("SELECT %s FROM %s WHERE %s=?;", COMPANY_STAR, COMPANY, COMPANY_ID);
    static final String COMPANY_BY_NAME = String.format("SELECT %s FROM %s WHERE %s LIKE ? ORDER BY %s LIMIT ?, ?;", COMPANY_STAR, COMPANY, COMPANY_NAME, COMPANY_NAME);
    static final String ALL_COMPANIES = String.format("SELECT %s FROM %s ORDER BY %s;", COMPANY_STAR, COMPANY, COMPANY_NAME);
    static final String ALL_COMPANIES_WITH_LIMIT = String.format("SELECT %s FROM %s ORDER BY %s LIMIT ?, ?;", COMPANY_STAR, COMPANY, COMPANY_NAME);

    static final String DELETE_COMPANY_WITH_ID = String.format("DELETE FROM %s WHERE %s = ?;", COMPANY, COMPANY_ID);
    static final String COMPUTER_IDS_WITH_COMPANY_ID = String.format("SELECT %s FROM %s LEFT JOIN %s ON %s=%s WHERE %s=?;", COMPUTER_ID, COMPUTER, COMPANY, COMPUTER_COMPANY_ID, COMPANY_ID, COMPANY_ID);
}

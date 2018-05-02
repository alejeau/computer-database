package com.excilys.formation.cdb.persistence.dao;

import com.excilys.formation.cdb.login.User;

public interface UserDAO {

    void createUser(User user);

    User getUserWithName(String username);

    void updateUser(User user);

    void deleteUser(String username);
}

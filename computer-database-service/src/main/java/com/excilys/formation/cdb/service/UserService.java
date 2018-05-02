package com.excilys.formation.cdb.service;

import com.excilys.formation.cdb.login.User;

public interface UserService {

    void createUser(User user);

    User getUserWithName(String username);

    void updateUser(User user);

    void deleteUser(String username);
}

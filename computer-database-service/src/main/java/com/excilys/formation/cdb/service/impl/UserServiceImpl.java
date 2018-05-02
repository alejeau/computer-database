package com.excilys.formation.cdb.service.impl;

import com.excilys.formation.cdb.login.User;
import com.excilys.formation.cdb.persistence.dao.UserDAO;
import com.excilys.formation.cdb.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("UserService")
public class UserServiceImpl implements UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void createUser(User user) {
        userDAO.createUser(user);
    }

    @Override
    public User getUserWithName(String username) {
        return userDAO.getUserWithName(username);
    }

    @Override
    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    @Override
    public void deleteUser(String username) {
        userDAO.deleteUser(username);
    }
}

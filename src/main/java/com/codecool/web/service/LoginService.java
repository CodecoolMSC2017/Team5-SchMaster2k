package com.codecool.web.service;

import com.codecool.web.dao.DatabaseUserDao;
import com.codecool.web.dao.UserDao;
import com.codecool.web.exception.InvalidUserException;
import com.codecool.web.model.User;

import java.sql.SQLException;

public class LoginService {

    UserDao userDao;

    public LoginService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User getUserByName(String name, String password) throws SQLException, InvalidUserException {
        User user = userDao.getUserByName(name);
        if (user != null) {
            if (user.getPassword().equals(password) &&user.getName().equals(name)) {
                return user;
            } else {
                throw new InvalidUserException();
            }
        }else{
            throw new InvalidUserException();
        }

    }



}

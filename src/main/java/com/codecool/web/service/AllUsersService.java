package com.codecool.web.service;

import com.codecool.web.dao.DatabaseUserDao;
import com.codecool.web.model.User;

import java.sql.SQLException;
import java.util.List;

public class AllUsersService {

    private DatabaseUserDao userDb;

    public AllUsersService(DatabaseUserDao userDb) {
        this.userDb = userDb;
    }

    public List<User> getAllUser() throws SQLException{
        return userDb.getAllUsers();
    }
}

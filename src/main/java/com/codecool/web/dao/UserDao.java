package com.codecool.web.dao;

import com.codecool.web.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {

    List<User> getAllUsers() throws SQLException;

    User getUserById(int userId) throws SQLException;

    User getUserByName(String name) throws SQLException;

    void addUser(String email, String password, String userName, String userRole) throws SQLException;

    void updateUser(int id, String name, String role)throws SQLException;

    void removeUser(User user) throws SQLException;


}

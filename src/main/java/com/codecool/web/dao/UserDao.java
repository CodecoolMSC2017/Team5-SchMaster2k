package com.codecool.web.dao;

import com.codecool.web.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {

    List<User> getAllUsers() throws SQLException;

    List<String> getAllUsersName() throws SQLException;

    List<String> getAllUsersEmail() throws SQLException;

    User getUserById(int userId) throws SQLException;

    User getUserByName(String name) throws SQLException;

    void addUser(String email, String password, String userName, String userRole) throws SQLException;

    void updateUser(int id, String name, String role)throws SQLException;

    void removeUser(User user) throws SQLException;

    void insertReg(String name, String fname, String lname, String pass, String email) throws SQLException;


}

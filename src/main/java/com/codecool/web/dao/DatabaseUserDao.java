package com.codecool.web.dao;


import com.codecool.web.exception.InvalidUserException;
import com.codecool.web.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class DatabaseUserDao extends AbstractDao implements UserDao {

    public DatabaseUserDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        List<User> allUser = new ArrayList<>();
        String sql = "SELECT * FROM users;";
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)){
            while(resultSet.next()){
                allUser.add(createUser(resultSet));
            }
        }

        return allUser;
    }

    @Override
    public List<String> getAllUsersName() throws SQLException {
        List<String> allUserName = new ArrayList<>();
        String sql = "SELECT name FROM users;";
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)){
            while(resultSet.next()){
                allUserName.add(resultSet.getString("name"));
            }
        }
        return allUserName;
    }

    public List<String> getAllUsersEmail() throws SQLException{
        List<String> allUserEmail = new ArrayList<>();
        String sql = "SELECT email FROM users;";
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)){
            while(resultSet.next()){
                allUserEmail.add(resultSet.getString("email"));
            }
        }
        return allUserEmail;
    }

    private User createUser(ResultSet resultSet) throws SQLException{
        int id=resultSet.getInt("id");
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
        String rank = resultSet.getString("rank");
        String pwd = resultSet.getString("password");
        return new User(id,name,email,rank,pwd);
    }

    @Override
    public User getUserById(int userId) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?;";
        User userById = null;
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,userId);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
               userById = createUser(resultSet);
            }
        }

        return userById;
        }

    @Override
    public User getUserByName(String name) throws SQLException {
        String sql = "SELECT * FROM users WHERE name = ?;";
        User userByName = null;
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,name);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                userByName = createUser(resultSet);
            }
        }

        return userByName;
    }


    @Override
    public void addUser(String email, String password, String name, String rank) throws SQLException {

        String sql = "INSERT INTO users (email,password,name,rank) " +
            "VALUES(?,?,?,?);";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            connection.setAutoCommit(false);
            statement.setString(1,email);
            statement.setString(2,password);
            statement.setString(3,name);
            statement.setString(4,rank);
            executeInsert(statement);
        }

    }

    @Override
    public void updateUser(int id, String name, String role) throws SQLException {

    }

    @Override
    public void removeUser(User user) throws SQLException {

    }

    @Override
    public void insertReg(String name, String fname, String lname, String password, String email) throws SQLException {
        if (fname == null || fname.equals("")) {
            fname = null;
        }
        if (lname == null || lname.equals("")) {
            lname = null;
        }
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "INSERT INTO users (name, first_name, last_name, password, email, rank) VALUES (?, ?, ?, ?, ?, 'User')";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, name);
            statement.setString(2, fname);
            statement.setString(3, lname);
            statement.setString(4, password);
            statement.setString(5, email);
            executeInsert(statement);
            int id = fetchGeneratedId(statement);
            connection.commit();

        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }
}

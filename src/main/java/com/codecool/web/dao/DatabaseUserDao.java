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
        String fName = resultSet.getString("first_name");
        String lName = resultSet.getString("last_name");
        boolean isOnline = resultSet.getBoolean("isOnline");
        return new User(id,name,fName,lName,email,rank,pwd,isOnline);
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
    public User loginGoogleUser(String email) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ?;";
        User userByEmail = null;
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,email);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                userByEmail = createUser(resultSet);
            }
        }

        return userByEmail;
    }
    @Override
    public void changeStatus(String email,String status) throws SQLException {
        if(status.equals("logout")){
            String sql = "UPDATE users SET isOnline = false WHERE email = ?;";
            User userByEmail = null;
            try (PreparedStatement statement = connection.prepareStatement(sql)){
                statement.setString(1,email);
                statement.executeUpdate();
            }
        }else if(status.equals("login")) {
            String sql = "UPDATE users SET isOnline = true WHERE email = ?;";
            User userByEmail = null;
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, email);
                statement.executeUpdate();
            }
        }


    }


    @Override
    public void addUser(String email, String name, String fname, String lname) throws SQLException {

        String sql = "INSERT INTO users (email, password, name, first_name, last_name, rank,isOnline) " +
            "VALUES(?, null, ?, ?, ?, 'User',false);";
        try (PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setString(1, email);
            statement.setString(2, name);
            statement.setString(3, fname);
            statement.setString(4, lname);
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

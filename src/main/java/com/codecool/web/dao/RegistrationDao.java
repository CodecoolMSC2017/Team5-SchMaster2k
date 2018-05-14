package com.codecool.web.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class RegistrationDao extends AbstractDao {

    public RegistrationDao(Connection connection) {
        super(connection);
    }

    public void insertReg(String name, String fname, String lname, String password, String email) throws SQLException{

        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "INSERT INTO users (name, first_name, last_name, password, email, rank) VALUES (?, ?, ?, ?, ?, 'User')";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
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

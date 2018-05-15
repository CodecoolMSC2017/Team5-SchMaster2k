package com.codecool.web.dao;

import com.codecool.web.model.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseTaskDao extends AbstractDao implements TaskDao {

    public DatabaseTaskDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<Task> getTasksByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM tasks WHERE user_id = (SELECT id FROM users WHERE name = ?);";
        List<Task> tasks = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()) {
                    tasks.add(fetchTask(resultSet));
                }
            }
        }
        return tasks;
    }

    private Task fetchTask(ResultSet rSet) throws SQLException {
        int id=rSet.getInt("id");
        String name = rSet.getString("name");
        int userId = rSet.getInt("user_id");
        return new Task(id,name,userId);

    }

    @Override
    public void addTask() throws SQLException {

    }

    @Override
    public void editTask(Task task) throws SQLException {

    }

    @Override
    public void deleteTask(Task task) throws SQLException {

    }

    @Override
    public Task getTaskById(int id) throws SQLException {
        return null;
    }
}

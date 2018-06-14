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

    @Override
    public List<Task> getTasksById(int id)throws SQLException{
        String sql = "SELECT * FROM tasks WHERE user_id = ?;";
        List<Task> tasks = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
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
    public void addTask(String taskName,int userId) throws SQLException {
        String sql = "INSERT INTO tasks(name,user_id) VALUES (?,?);";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, taskName);
            statement.setInt(2,userId);
            executeInsert(statement);

        }
    }

    @Override
    public void editTask(String s, int id) throws SQLException {
        String sql = "UPDATE tasks SET name=? WHERE id = ?;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1,s);
            statement.setInt(2,id);
            statement.executeUpdate();

        }
    }

    @Override
    public void deleteTask(int taskId) throws SQLException {
        String sql = "begin;" +
            "DELETE FROM tasks WHERE id = ?;" +
            "DELETE FROM hours WHERE task_id = ?;" +
            "commit;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, taskId);
            statement.setInt(2, taskId);
            statement.executeUpdate();
        }
    }

    public void deleteTaskInsertlog(int taskId, String message, String adminName, int userId) throws SQLException {
        String insSql = "INSERT INTO delLog(del_time, user_id, task_name, admin_name, message)" +
            " VALUES (CURRENT_TIMESTAMP,?,(SELECT name FROM tasks WHERE id = ?),?,?)";
        try (PreparedStatement insStatement = connection.prepareStatement(insSql)) {
            insStatement.setInt(1, userId);
            insStatement.setInt(2, taskId);
            insStatement.setString(3, adminName);
            insStatement.setString(4, message);
            executeInsert(insStatement);

            String sql = "begin;" +
                "DELETE FROM tasks WHERE id = ?;" +
                "DELETE FROM hours WHERE task_id = ?;" +
                "commit;";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, taskId);
                statement.setInt(2, taskId);
                statement.executeUpdate();

            }
        }
    }

    @Override
    public Task getTaskById(int id) throws SQLException, NullPointerException {
        String sql = "SELECT * FROM tasks WHERE id = ?;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if(resultSet.next()){
                    return fetchTask(resultSet);
                }

            }
        }
        return null;
    }
}

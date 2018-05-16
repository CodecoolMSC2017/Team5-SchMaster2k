package com.codecool.web.dao;

import com.codecool.web.model.Day;
import com.codecool.web.model.Schedule;
import com.codecool.web.model.Task;
import com.codecool.web.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SchDao extends AbstractDao{

    public SchDao(Connection connection) {
        super(connection);
    }

    public List<Schedule> getAllSchByUserId(int id) throws SQLException{
        String sql = "SELECT * FROM schedules WHERE user_id = ?";
        List<Schedule> schedules = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                schedules.add(createSch(resultSet));
            }
        }
        return schedules;
    }

    public Schedule getScheduleById(int id) throws SQLException{
        String sql = "SELECT * FROM schedules WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return createSch(resultSet);
            }
        }
        return null;
    }

    private Schedule createSch(ResultSet resultSet) throws SQLException{
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String content = resultSet.getString("content");
        return new Schedule(id, name, content);
    }

    public List<Day> getScheduleDaysByID(int id) throws SQLException {
        String sql = "SELECT * FROM days WHERE schedule_id = ?;";
        List<Day> days = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                days.add(createDay(resultSet));
            }
        }
        return days;
    }

    private Day createDay(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        Day day = new Day(id, name);
        day.setTasks(getTasksByDayID(id));
        return day;
    }
    
    private List<Task> getTasksByDayID(int id) throws SQLException {
        String sql = "SELECT task_id, day_id, tasks.name, tasks.user_id from hours " +
            "JOIN tasks ON hours.task_id = tasks.id " +
            "WHERE day_id = ? " +
            "GROUP BY task_id, day_id, tasks.name, tasks.user_id;";
        List<Task> tasks = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tasks.add(fetchTask(resultSet));
            }
        }
        return tasks;
    }

    private Task fetchTask(ResultSet rSet) throws SQLException {
        int id=rSet.getInt("task_id");
        String name = rSet.getString("name");
        int userId = rSet.getInt("user_id");
        return new Task(id,name,userId);
    }

    public void addSchedule(String title, String content, int userId) throws SQLException {
        String sql = "INSERT INTO schedules (name, content, user_id) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, title);
            statement.setString(2, content);
            statement.setInt(3, userId);
            executeInsert(statement);
        }
    }
}

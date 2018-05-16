package com.codecool.web.dao;

import com.codecool.web.model.Day;
import com.codecool.web.model.Schedule;
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
        return new Day(id, name);
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

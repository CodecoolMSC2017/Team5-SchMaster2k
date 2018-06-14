package com.codecool.web.dao;

import com.codecool.web.model.Day;
import com.codecool.web.model.Schedule;
import com.codecool.web.model.Task;
import com.codecool.web.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseSchDao extends AbstractDao{

    public DatabaseSchDao(Connection connection) {
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

    public int getMaxDayId() throws SQLException {
        String sql = "SELECT MAX(id) AS schId FROM schedules";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return resultSet.getInt("schId");
            }
        }
        return 0;


    }
    public void addScheduleHelper(String title, String content, int userId) throws SQLException {
        String sql = "INSERT INTO schedules (name, content, user_id) VALUES (?, ?, ?);";



        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, title);
            statement.setString(2, content);
            statement.setInt(3, userId);


            executeInsert(statement);
        }
    }



    public void addSchedule(String title, String content, int userId) throws SQLException {
        addScheduleHelper(title,content,userId);
        String sql =  "INSERT INTO days (name, name_id, schedule_id) VALUES" +
                    "('Monday', 'mo', ?), " +
                    "('Tuesday', 'tu', ?), " +
                    "('Wednesday', 'we', ?), " +
                    "('Thursday', 'th', ?), " +
                    "('Friday', 'fr', ?), " +
                    "('Saturday', 'sa', ?), " +
                    "('Sunday', 'su', ?); ";



        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, getMaxDayId());
            statement.setInt(2, getMaxDayId());
            statement.setInt(3, getMaxDayId());
            statement.setInt(4, getMaxDayId());
            statement.setInt(5, getMaxDayId());
            statement.setInt(6, getMaxDayId());
            statement.setInt(7, getMaxDayId());

            executeInsert(statement);
        }
    }

    public Map<Integer, String> getDaysByUserIDandSchID(int userId, int schId) throws SQLException{
        String sql = "SELECT days.id, name_id FROM days " +
            "JOIN schedules ON days.schedule_id = schedules.id " +
            "WHERE user_id = ? AND schedule_id = ?";
        Map<Integer, String> days = new HashMap<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1,userId);
            statement.setInt(2,schId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                days.put(resultSet.getInt("id"), resultSet.getString("name_id"));
            }
        }
        return days;

    }

    public List<Integer> getTasksId(int userId, int schId, int day_id) throws SQLException{
        String sql = "SELECT task_day_sch.task_id FROM schedules " +
            "JOIN days ON schedules.id = days.schedule_id " +
            "JOIN task_day_sch ON days.id = task_day_sch.day_id " +
            "WHERE user_id = ? AND schedules.id = ? AND day_id = ?";
        List<Integer> tasks = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1,userId);
            statement.setInt(2,schId);
            statement.setInt(3,day_id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tasks.add(resultSet.getInt("task_id"));

            }
        }
        return tasks;

    }

    public List<Integer> getTask(int dayId, int taskId) throws SQLException{
        String sql = "SELECT * FROM hours " +
                    "WHERE day_id = ? And task_id = ?";
        List<Integer> taskHours = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1,dayId);
            statement.setInt(2,taskId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                taskHours.add(resultSet.getInt("name"));
            }
        }
        return taskHours;
    }

    public int getNoOfSch(int id) throws SQLException{
        String sql = "SELECT COUNT(*) AS schNo FROM schedules WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                return rs.getInt("schNo");
            }
        }
        return 0;
    }

    public int getNoOfTask(int id) throws SQLException{
        String sql = "SELECT COUNT(*) AS taskNo FROM tasks WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                return rs.getInt("taskNo");
            }
        }
        return 0;
    }

    public int isInSch(int taskId, int schId) throws SQLException {
        String sql = "SELECT COUNT(task_id) AS count FROM task_day_sch WHERE task_id = ? and schedule_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, taskId);
            statement.setInt(2, schId);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                if(rs.getInt("count")==0) {
                    return -1;
                }
            }
        }
        return 1;


    }

    public void insertTaskToSch(int hourName, int dayId, int taskId, int schId) throws SQLException{

        String sql ="BEGIN;"+
                    "INSERT INTO hours (name, task_id, day_id) VALUES (?, ?, ?);" +
                    "INSERT INTO task_day_sch (day_id, task_id, schedule_id) VALUES (?, ?, ?);"+
                    "COMMIT;";


        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, hourName);
            statement.setInt(2, taskId);
            statement.setInt(3, dayId);

            statement.setInt(4, dayId);
            statement.setInt(5, taskId);
            statement.setInt(6, schId);
            executeInsert(statement);
        }  catch (SQLException e) {

        }
    }

    public void deleteTaskFromSch(int dayId, int taskId, int schId) throws SQLException {

        String sql = "begin; " +
            "delete from task_day_sch where task_id = ? and schedule_id = ? and day_id = ?; " +
            "delete from hours where task_id = ? and day_id = ?; " +
            "commit;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, taskId);
            statement.setInt(2, schId);
            statement.setInt(3, dayId);

            statement.setInt(4, taskId);
            statement.setInt(5, dayId);
            statement.executeUpdate();

        } catch (SQLException e) {

        }
    }

    public int findDayId(String day, int schId) throws SQLException{
        String sql = "SELECT  id FROM days WHERE name_id = ? AND schedule_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, day);
            statement.setInt(2, schId);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                return rs.getInt("id");
            }
        }
        return 0;
    }

    public void deleteSch(int schId) throws SQLException {

        String sql = "delete from schedules where id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, schId);
            statement.executeUpdate();

        } catch (SQLException e) {

        }
    }

    public int getNoOnlineUsers() throws SQLException{
        String sql = "SELECT COUNT(*) AS OnlineNo FROM users WHERE isOnline = true";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                return rs.getInt("OnlineNo");
            }
        }
        return 0;
    }
}

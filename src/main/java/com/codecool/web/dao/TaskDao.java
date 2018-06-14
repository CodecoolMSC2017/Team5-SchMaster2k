package com.codecool.web.dao;

import com.codecool.web.model.Task;

import java.sql.SQLException;
import java.util.List;

public interface TaskDao {

    List<Task> getTasksByUsername(String username)throws SQLException;

    List<Task> getTasksById(int id)throws SQLException;

    void addTask(String taskName,int userId)throws SQLException;

    void editTask(String s,int id)throws SQLException;

    void deleteTask(int taskId)throws SQLException;

    void deleteTaskInsertlog(int taskId, String message, String adminName, int userID)throws SQLException;

    Task getTaskById(int id)throws SQLException;

}

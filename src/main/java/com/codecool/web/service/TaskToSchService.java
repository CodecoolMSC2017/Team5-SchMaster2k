package com.codecool.web.service;

import com.codecool.web.dao.DatabaseSchDao;

import java.sql.SQLException;

public class TaskToSchService {

    private DatabaseSchDao db;

    public TaskToSchService(DatabaseSchDao db) {
        this.db = db;
    }

    private int getDayId(String dayHour, int schId) throws SQLException {
        String day = dayHour.substring(0,2);
        int dayId = db.findDayId(day, schId);
        return dayId;
    }

    public void addTaskToSch(String dayHour, int taskId, int schId, int taskLength) throws SQLException {
        int hourName = Integer.valueOf(dayHour.substring(2, dayHour.length()));
        if (db.isInSch(taskId, schId) == -1) {
            for (int i = hourName; i < hourName + taskLength; i++) {
                db.insertTaskToSch(i, getDayId(dayHour, schId), taskId, schId);

            }


        }
    }

    public void removeTaskFromSch(String dayHour, int taskId, int schId) throws SQLException {
        db.deleteTaskFromSch(getDayId(dayHour, schId), taskId, schId);

    }
}

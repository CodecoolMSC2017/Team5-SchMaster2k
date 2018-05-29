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

    public void addTaskToSch(String dayHour, int taskId, int schId) throws SQLException {
        int hourName = Integer.valueOf(dayHour.substring(2,dayHour.length()));
        db.insertTaskToSch(hourName, getDayId(dayHour, schId), taskId, schId);



    }
}

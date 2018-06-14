package com.codecool.web.service;

import com.codecool.web.dao.DatabaseSchDao;
import com.codecool.web.dto.SchTaskNoDTO;
import com.codecool.web.listener.OnlineUsersCounter;
import com.codecool.web.model.Day;
import com.codecool.web.model.Schedule;

import java.sql.SQLException;
import java.util.List;

public class SchService{

    private DatabaseSchDao db;

    public SchService(DatabaseSchDao db) {
        this.db = db;
    }

    public List<Schedule> getSchedulesByID(String id) throws SQLException{
        return db.getAllSchByUserId(Integer.parseInt(id));
    }

    public Schedule getScheduleByID(int id) throws SQLException{
        return db.getScheduleById(id);
    }

    public List<Day> getScheduleDays(int id) throws SQLException {
        return db.getScheduleDaysByID(id);
    }
    public SchTaskNoDTO getNoOfSchTask(int id) throws SQLException{
        return new SchTaskNoDTO(db.getNoOfSch(id), db.getNoOfTask(id), db.getNoOnlineUsers());
    }

    public void deleteSchedule(int schId) throws SQLException {
        db.deleteSch(schId);
    }
}

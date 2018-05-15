package com.codecool.web.service;

import com.codecool.web.dao.SchDao;
import com.codecool.web.model.Schedule;

import java.sql.SQLException;
import java.util.List;

public class SchService{

    private SchDao db;

    public SchService(SchDao db) {
        this.db = db;
    }

    public List<Schedule> getSchedulesByID(String id) throws SQLException{
        return db.getAllSchByUserId(Integer.parseInt(id));
    }

    public Schedule getScheduleByID(int id) throws SQLException{
        return db.getScheduleById(id);
    }
}

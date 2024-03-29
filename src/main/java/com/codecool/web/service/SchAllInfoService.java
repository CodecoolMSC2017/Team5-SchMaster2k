package com.codecool.web.service;

import com.codecool.web.dao.DatabaseSchDao;
import com.codecool.web.dao.DatabaseTaskDao;
import com.codecool.web.dao.TaskDao;
import com.codecool.web.model.Task;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SchAllInfoService {

    private DatabaseSchDao db;
    private TaskDao taskDb;

    public SchAllInfoService(DatabaseSchDao db, TaskDao taskDb) {

        this.db = db;
        this.taskDb = taskDb;
    }

    public SchAllInfoService(DatabaseSchDao db) {

        this.db = db;

    }



    public Map<String, Task> getTasksMap(int userId, int schId) throws SQLException{

        Map<Integer,String> dayIdandName = db.getDaysByUserIDandSchID(userId, schId);
        Map<String, Task> result = new HashMap<>();

        for(int key:dayIdandName.keySet()){
            List<Integer> tasksId = db.getTasksId(userId, schId, key);
            String dayName = dayIdandName.get(key);

            for(Integer i:tasksId){
                List<Integer> taskHours= db.getTask(key, i);


                for (Integer j: taskHours){

                    result.put(dayName + j, taskDb.getTaskById(i));


                }
            }
        }
        return result;
    }
}

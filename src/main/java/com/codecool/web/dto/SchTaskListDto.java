package com.codecool.web.dto;

import com.codecool.web.model.Schedule;
import com.codecool.web.model.Task;

import java.util.List;

public class SchTaskListDto {

    private int userId;
    private List<Schedule> schList;
    private List<Task> taskList;

    public SchTaskListDto(int userId, List<Schedule> schList, List<Task> taskList) {
        this.userId = userId;
        this.schList = schList;
        this.taskList = taskList;
    }

    public List<Schedule> getSchList() {
        return schList;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public int getUserId() {
        return userId;
    }
}

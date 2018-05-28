package com.codecool.web.dto;

public class SchTaskNoDTO {

    int schedule;
    int task;

    public SchTaskNoDTO(int schedule, int task) {
        this.schedule = schedule;
        this.task = task;
    }

    public int getSchedule() {
        return schedule;
    }

    public int getTask() {
        return task;
    }
}

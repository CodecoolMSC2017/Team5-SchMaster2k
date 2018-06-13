package com.codecool.web.dto;

public class SchTaskNoDTO {

    private int schedule;
    private int task;
    private int onlineNumber;

    public SchTaskNoDTO(int schedule, int task, int onlineNumber) {
        this.schedule = schedule;
        this.task = task;
        this.onlineNumber = onlineNumber;
    }

    public int getSchedule() {
        return schedule;
    }

    public int getTask() {
        return task;
    }

    public int getOnlineNumber() {
        return onlineNumber;
    }
}

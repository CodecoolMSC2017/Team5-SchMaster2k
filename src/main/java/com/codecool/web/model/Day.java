package com.codecool.web.model;

import java.util.List;

public class Day extends AbstractModel {

    private String name;
    private List<Task> tasks;

    public Day(int id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}

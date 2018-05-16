package com.codecool.web.model;

public class Day extends AbstractModel {

    private String name;

    public Day(int id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

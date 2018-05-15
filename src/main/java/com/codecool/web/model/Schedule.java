package com.codecool.web.model;

public class Schedule extends AbstractModel{

    private String name;
    private String content;

    public Schedule(int id, String name, String content) {
        super(id);
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }
}

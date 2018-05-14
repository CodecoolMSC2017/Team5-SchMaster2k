package com.codecool.web.model;

public class User extends AbstractModel {

    private String name;
    private String email;
    private String password;
    private String rank;

    public User(int id,String name,String email,String rank,String password){
        super(id);
        this.name=name;
        this.email=email;
        this.password=password;
        this.rank=rank;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRank() {
        return rank;
    }
}

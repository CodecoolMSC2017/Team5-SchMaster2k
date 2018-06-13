package com.codecool.web.model;

public class User extends AbstractModel {

    private String name;
    private String fName;
    private String lName;
    private String email;
    private String password;
    private String rank;
    private boolean isOnline;

    public User(int id,String name,String fName,String lName,String email,String rank,String password, boolean isOnline){
        super(id);
        this.isOnline = isOnline;
        this.name=name;
        this.fName=fName;
        this.lName=lName;
        this.email=email;
        this.password=password;
        this.rank=rank;
    }

    public boolean isOnline() {
        return isOnline;
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

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }
}


package com.codecool.web.service;

import com.codecool.web.dao.UserDao;
import com.codecool.web.model.User;

import javax.naming.NameNotFoundException;
import java.sql.SQLException;
import java.util.List;

public class RegistrationService {

    private UserDao db;

    public RegistrationService(UserDao db) {
        this.db = db;
    }

    public boolean checkEmail(String email) throws SQLException{
        List<String> emails = db.getAllUsersEmail();
        for(String e: emails){
            if(e.toLowerCase().equals(email.toLowerCase())){
                return true;
            }
        }return false;
    }

    public boolean checkReg(String name, String email) throws SQLException{
        List<String> names = db.getAllUsersName();
        return names.contains(name) || checkEmail(email);
    }

    public void createReg(String name, String fname, String lname, String pass, String email) throws SQLException, NameNotFoundException{
        if(name == null || name.equals("")){
            throw new NameNotFoundException("There is no name");
        }
        if(email == null || email.equals("")){
            throw new NameNotFoundException("There is no email");
        }
        if(pass == null || pass.equals("")){
            throw new NameNotFoundException("There is no password");
        }
        db.insertReg(name, fname, lname, pass, email);
    }

}

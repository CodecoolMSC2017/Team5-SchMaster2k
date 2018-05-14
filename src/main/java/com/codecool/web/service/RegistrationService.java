package com.codecool.web.service;

import com.codecool.web.dao.RegistrationDao;
import com.codecool.web.model.User;

import javax.naming.NameNotFoundException;
import java.sql.SQLException;

public class RegistrationService {

    private RegistrationDao db;

    public RegistrationService(RegistrationDao db) {
        this.db = db;
    }

    public void createReg(String name, String fname, String lname, String pass, String email) throws SQLException, NameNotFoundException{
        if(name == null || name.equals("")){
            throw new NameNotFoundException("There is no name");
        }
        if(pass == null || pass.equals("")){
            throw new NameNotFoundException("There is no email");
        }
        db.insertReg(name, fname, lname, pass, email);
    }
}

package com.codecool.web.service;

import com.codecool.web.dao.RegistrationDao;
import com.codecool.web.model.User;

import javax.naming.NameNotFoundException;

public class RegistrationService {

    private RegistrationDao db;

    public RegistrationService(RegistrationDao db) {
        this.db = db;
    }

    public User createReg(String name, String fname, String lname, String pass, String email) throws NameNotFoundException{
        if(name == null || name.equals("")){
            throw new NameNotFoundException("There is no name");
        }
        if(pass == null || pass.equals("")){
            throw new NameNotFoundException("There is no email");
        }
    }
}

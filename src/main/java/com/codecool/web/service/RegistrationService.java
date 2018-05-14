package com.codecool.web.service;

import com.codecool.web.dao.RegistrationDao;
import com.codecool.web.model.User;

import javax.naming.NameNotFoundException;

public class RegistrationService {

    private RegistrationDao db;

    public RegistrationService(RegistrationDao db) {
        this.db = db;
    }

    public User createReg(String name, String fname, String lname, String email, String password) throws NameNotFoundException {
        if (name == null || name.equals("")) {
            throw new NameNotFoundException();
        }
        else if (email == null || email.equals("")) {
            throw new NameNotFoundException();
        }
        else if (password == null || password.equals("")) {
            throw new NameNotFoundException();
        }

    }

}

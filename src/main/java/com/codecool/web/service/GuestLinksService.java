package com.codecool.web.service;

import com.codecool.web.dao.GuestLinkDao;

import java.sql.SQLException;

public class GuestLinksService {

    private GuestLinkDao db;

    public GuestLinksService(GuestLinkDao db) {
        this.db = db;
    }

    public void insertGuestLink(int userId, int schId){

    }

    public void deleteGuestLink(int userId, int schId){
        db.deleteGuestLink(userId, schId);
    }

}

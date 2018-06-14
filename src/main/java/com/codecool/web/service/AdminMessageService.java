package com.codecool.web.service;

import com.codecool.web.dao.AdminMessageDao;

import java.sql.SQLException;
import java.util.List;

public class AdminMessageService {

    private AdminMessageDao db;

    public AdminMessageService(AdminMessageDao db) {
        this.db = db;
    }

    public List<List<String>> getUserMessages(int userId) throws SQLException{
        return db.getAdminMessagesByUserId(userId);
    }
}

package com.codecool.web.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GuestLinkDao extends AbstractDao{

    public GuestLinkDao(Connection connection) {
        super(connection);
    }

    public void insertIdsToShareTable(int userId, int schId) throws SQLException{
        String sql = "INSERT INTO shared(schedule_id, user_id) VALUES (?,?);";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, schId);
            statement.setInt(2, userId);
            executeInsert(statement);
        }
    }

    public void fnForDeleteRENAME(){

    }

    public void fnForDeleteRENAME22(){

    }
}

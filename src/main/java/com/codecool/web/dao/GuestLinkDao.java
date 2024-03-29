package com.codecool.web.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    public void deleteIdsToShareTable(int userId, int schId) throws SQLException {
        String sql = "DELETE FROM shared WHERE user_id = ? AND schedule_id = ?;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setInt(2, schId);
            statement.executeUpdate();
        }
    }

    public boolean checkSharedLink(int userId, int schId ) throws SQLException{
        String sql = "SELECT COUNT(*) AS count FROM shared WHERE user_id = ? AND schedule_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,userId);
            statement.setInt(2,schId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt("count") != 0;
            }
        }
        return true;
    }
}

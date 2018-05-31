package com.codecool.web.dao;

import java.sql.*;

public class GuestLinkDao extends AbstractDao{

    public GuestLinkDao(Connection connection) {
        super(connection);
    }

    public void fnForInsertRENAME(){

    }

    public void fnForInsertRENAME22(){

    }

    public void deleteGuestLink(int userId, int schId){
        String sql = "DELETE FROM shared WHERE user_id = ? AND schedule_id = ?;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "user_id");
            statement.setString(2, "schedule_id");
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void fnForDeleteRENAME22(){

    }
}

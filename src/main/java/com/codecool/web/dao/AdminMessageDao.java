package com.codecool.web.dao;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AdminMessageDao extends AbstractDao{

    public AdminMessageDao(Connection connection) {
        super(connection);
    }

    public List<List<String>> getAdminMessagesByUserId(int userId) throws SQLException{
        String sql = "SELECT * FROM delLog WHERE user_id = ?";
        List<List<String>> messages = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                messages.add(createMessage(rs));
            }
        }
        return messages;
    }

    private List<String> createMessage(ResultSet rs) throws SQLException{
        List<String> result = new ArrayList<>();
        Timestamp delTime = rs.getTimestamp("del_time");
        String taskName = rs.getString("task_name");
        String adminName = rs.getString("admin_name");
        String message = rs.getString("message");
        result.add(taskName);
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String stringDate = df.format(delTime);
        result.add(stringDate);
        result.add(adminName);
        result.add(message);
        return result;
    }
}

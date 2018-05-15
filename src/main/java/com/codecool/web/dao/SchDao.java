package com.codecool.web.dao;

import com.codecool.web.model.Schedule;
import com.codecool.web.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SchDao extends AbstractDao{

    public SchDao(Connection connection) {
        super(connection);
    }

    public List<Schedule> getAllSchByUserId(int id) throws SQLException{
        String sql = "SELECT * FROM schedules WHERE user_id = ?";
        List<Schedule> schedules = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                schedules.add(createSch(resultSet));
            }
        }
        return schedules;
    }

    public Schedule createSch(ResultSet resultSet) throws SQLException{
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String content = resultSet.getString("content");
        return new Schedule(id, name, content);
    }
}

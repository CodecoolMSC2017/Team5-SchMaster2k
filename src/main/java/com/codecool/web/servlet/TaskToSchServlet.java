package com.codecool.web.servlet;


import com.codecool.web.dao.DatabaseSchDao;
import com.codecool.web.dao.DatabaseTaskDao;
import com.codecool.web.dao.TaskDao;
import com.codecool.web.dto.MessageDto;
import com.codecool.web.model.Task;
import com.codecool.web.model.User;
import com.codecool.web.service.SchAllInfoService;
import com.codecool.web.service.TaskToSchService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@WebServlet("/taskToSchServlet")
public class TaskToSchServlet extends AbstractServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            DatabaseSchDao schDao = new DatabaseSchDao(connection);
            TaskToSchService service = new TaskToSchService(schDao);
            SchAllInfoService schService = new SchAllInfoService(schDao);

            service.addTaskToSch(req.getParameter("dayHour"), Integer.parseInt(req.getParameter("taskId")), Integer.parseInt(req.getParameter("schId")));
            int userId = Integer.parseInt(req.getParameter("userId"));
            Map<String,String> mapOfTasks = schService.getTasksMap(userId, Integer.parseInt(req.getParameter("schId")));
            resp.setContentType("application/json");
            sendMessage(resp, HttpServletResponse.SC_OK, mapOfTasks);


        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }

    }
}

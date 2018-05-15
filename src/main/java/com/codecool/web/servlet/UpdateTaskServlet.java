package com.codecool.web.servlet;

import com.codecool.web.dao.DatabaseTaskDao;
import com.codecool.web.dao.DatabaseUserDao;
import com.codecool.web.dao.TaskDao;
import com.codecool.web.dao.UserDao;

import com.codecool.web.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


@WebServlet("/updateTask")
public final class UpdateTaskServlet extends AbstractServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            TaskDao tDao = new DatabaseTaskDao(connection);
            String taskName = req.getParameter("updatedName");
            int taskId = Integer.parseInt(req.getParameter("taskId"));
            tDao.editTask(taskName,taskId);
            String updatedName = tDao.getTaskById(taskId).getName();
            resp.setContentType("application/json");
            sendMessage(resp, HttpServletResponse.SC_OK, updatedName);
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }
}

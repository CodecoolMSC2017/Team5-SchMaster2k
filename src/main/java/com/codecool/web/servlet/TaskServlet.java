package com.codecool.web.servlet;

import com.codecool.web.dao.DatabaseTaskDao;
import com.codecool.web.dao.DatabaseUserDao;
import com.codecool.web.dao.TaskDao;
import com.codecool.web.dao.UserDao;

import com.codecool.web.model.Task;
import com.codecool.web.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;


@WebServlet("/taskServlet")
public final class TaskServlet extends AbstractServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            TaskDao tDao = new DatabaseTaskDao(connection);
            if(req.getParameter("taskAddingField")!=null){
                String taskName=req.getParameter("taskAddingField");
                User user=(User)req.getSession().getAttribute("user");
                int userId=user.getId();
                tDao.addTask(taskName,userId);
            }

            if(req.getParameter("taskIdToDelete")!=null){
                int taskId=Integer.parseInt(req.getParameter("taskIdToDelete"));
                tDao.deleteTask(taskId);
            }
            String username = req.getParameter("username");
            List<Task> tasks = tDao.getTasksByUsername(username);

            resp.setContentType("application/json");
            sendMessage(resp, HttpServletResponse.SC_OK, tasks);


        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }

    }
}

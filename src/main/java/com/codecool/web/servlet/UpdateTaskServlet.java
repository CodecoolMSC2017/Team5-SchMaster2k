package com.codecool.web.servlet;

import com.codecool.web.dao.DatabaseTaskDao;
import com.codecool.web.dao.DatabaseUserDao;
import com.codecool.web.dao.TaskDao;
import com.codecool.web.dao.UserDao;

import com.codecool.web.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


@WebServlet("/updateTask")
public final class UpdateTaskServlet extends AbstractServlet {

    private static final Logger logger = Logger.getLogger(UpdateTaskServlet.class);
    private User user;
    private int taskId;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            user = (User)req.getSession().getAttribute("user");
            TaskDao tDao = new DatabaseTaskDao(connection);
            String taskName = req.getParameter("updatedName");
            taskId = Integer.parseInt(req.getParameter("taskId"));
            tDao.editTask(taskName,taskId);
            String updatedName = tDao.getTaskById(taskId).getName();
            resp.setContentType("application/json");
            sendMessage(resp, HttpServletResponse.SC_OK, updatedName);
            logger.info(user.getName() + ": Task update done. Task id: " + taskId );
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
            logger.error(user.getName() + ": Error.", ex);
        }
    }
}

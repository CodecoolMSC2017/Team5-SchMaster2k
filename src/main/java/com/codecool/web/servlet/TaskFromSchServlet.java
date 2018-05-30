package com.codecool.web.servlet;


import com.codecool.web.dao.DatabaseSchDao;
import com.codecool.web.dao.DatabaseTaskDao;
import com.codecool.web.dao.TaskDao;
import com.codecool.web.model.Task;
import com.codecool.web.model.User;
import com.codecool.web.service.SchAllInfoService;
import com.codecool.web.service.TaskToSchService;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

@WebServlet("/taskFromSchServlet")
public class TaskFromSchServlet extends AbstractServlet {

    private static final Logger logger = Logger.getLogger(TaskFromSchServlet.class);
    private User user;
    private String dayHour;
    private int taskId;
    private int schId;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            DatabaseSchDao schDao = new DatabaseSchDao(connection);
            TaskDao taskDao = new DatabaseTaskDao(connection);
            TaskToSchService service = new TaskToSchService(schDao);
            SchAllInfoService schService = new SchAllInfoService(schDao,taskDao);
            user = (User) req.getSession().getAttribute("user");

            dayHour = req.getParameter("dayHour");
            taskId = Integer.parseInt(req.getParameter("taskId"));
            schId = Integer.parseInt(req.getParameter("schId"));

            service.removeTaskFromSch(dayHour, taskId, schId);
            int userId = Integer.parseInt(req.getParameter("userId"));
            Map<String,Task> mapOfTasks = schService.getTasksMap(userId, Integer.parseInt(req.getParameter("schId")));
            resp.setContentType("application/json");
            sendMessage(resp, HttpServletResponse.SC_OK, mapOfTasks);

            logger.info(user.getName() + ": Add task(" +taskId+ ") in sch(" +schId+ ") at hour(" +dayHour+ ")"  );
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
            logger.error(user.getName() + "error at:  Add task(" +taskId+ ") in sch(" +schId+ ") at hour(" +dayHour+ ")");
        }

    }
}

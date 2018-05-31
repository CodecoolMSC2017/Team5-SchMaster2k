package com.codecool.web.servlet;


import com.codecool.web.dao.DatabaseSchDao;
import com.codecool.web.dao.DatabaseTaskDao;
import com.codecool.web.dao.TaskDao;
import com.codecool.web.dto.MessageDto;
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
import java.util.List;
import java.util.Map;

@WebServlet("/protected/taskToSchServlet")
public class TaskToSchServlet extends AbstractServlet {

    private static final Logger logger = Logger.getLogger(TaskToSchServlet.class);
    private User user;
    private String dayHour;
    private int taskId;
    private int schId;
    private int taskLength;

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

            taskLength=Integer.parseInt(req.getParameter("taskLength"));




            int statusNumber = service.addTaskToSch(dayHour, taskId, schId, taskLength);
            int userId = Integer.parseInt(req.getParameter("userId"));
            Map<String,Task> mapOfTasks = schService.getTasksMap(userId, Integer.parseInt(req.getParameter("schId")));
            resp.setContentType("application/json");
            if(statusNumber==-1){
                sendMessage(resp, HttpServletResponse.SC_OK, new MessageDto("-1"));
            }
            sendMessage(resp, HttpServletResponse.SC_OK, new MessageDto("1"));



            logger.info(user.getName() + ": Add task(" +taskId+ ") in sch(" +schId+ ") at hour(" +dayHour+ ")"  );
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
            logger.error(user.getName() + "error at:  Add task(" +taskId+ ") in sch(" +schId+ ") at hour(" +dayHour+ ")");
        }

    }
}

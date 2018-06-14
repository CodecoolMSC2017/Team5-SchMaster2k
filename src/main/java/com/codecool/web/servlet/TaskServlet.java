package com.codecool.web.servlet;

import com.codecool.web.dao.DatabaseTaskDao;
import com.codecool.web.dao.TaskDao;
import com.codecool.web.model.Task;
import com.codecool.web.model.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;



@WebServlet("/protected/taskServlet")
public final class TaskServlet extends AbstractServlet {

    private static final Logger logger = Logger.getLogger(TaskServlet.class);
    private User user;

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            user = (User) req.getSession().getAttribute("user");
            TaskDao tDao = new DatabaseTaskDao(connection);
            int userId = Integer.parseInt(req.getParameter("userId"));
            int taskId = Integer.parseInt(req.getParameter("taskId"));
            tDao.deleteTaskInsertlog(taskId, "default", user.getName(), userId);

            logger.info(user.getName() + ": Task Delet done by admin for (userID): " + userId);
            resp.setStatus(HttpServletResponse.SC_OK);

        } catch (SQLException ex) {
            handleSqlError(resp, ex);
            logger.error(user.getName() + ": Error.", ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try (Connection connection = getConnection(req.getServletContext())) {
            user = (User) req.getSession().getAttribute("user");
            TaskDao tDao = new DatabaseTaskDao(connection);
            if(req.getParameter("taskAddingField")!=null){
                String taskName=req.getParameter("taskAddingField");
                int userId=user.getId();
                tDao.addTask(taskName,userId);
                logger.info(user.getName() + ": Task add done. Task name: " + taskName );
            }

            if(req.getParameter("taskIdToDelete")!=null){
                int taskId=Integer.parseInt(req.getParameter("taskIdToDelete"));
                tDao.deleteTask(taskId);
                logger.info(user.getName() + ": Task delete done. Task id: " + taskId);
            }
            String username = req.getParameter("username");
            List<Task> tasks = tDao.getTasksByUsername(username);

            resp.setContentType("application/json");
            sendMessage(resp, HttpServletResponse.SC_OK, tasks);


        } catch (SQLException ex) {
            handleSqlError(resp, ex);
            logger.error(user.getName() + ": Error.", ex);
        }

    }
}

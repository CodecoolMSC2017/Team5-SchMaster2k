package com.codecool.web.servlet;

import com.codecool.web.dao.DatabaseSchDao;
import com.codecool.web.dao.DatabaseTaskDao;
import com.codecool.web.model.Task;
import com.codecool.web.model.User;
import com.codecool.web.service.SchAllInfoService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

@WebServlet("/guestLink")
public class GuestLinkServlet extends AbstractServlet {

    private static final Logger logger = Logger.getLogger(GuestLinkServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int userId = Integer.parseInt(req.getParameter("id"));
        int schId = Integer.parseInt(req.getParameter("schid"));

        req.setAttribute("userId", userId);
        req.setAttribute("schId", schId);

        req.getRequestDispatcher("guest.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection c = getConnection(getServletContext())) {
            DatabaseSchDao db = new DatabaseSchDao(c);
            DatabaseTaskDao taskDb = new DatabaseTaskDao(c);
            SchAllInfoService service = new SchAllInfoService(db, taskDb);

            int userId = Integer.parseInt(req.getParameter("userId"));
            int schId = Integer.parseInt(req.getParameter("schId"));

            Map<String,Task> mapOfTasks = service.getTasksMap(userId, schId);

            sendMessage(resp, HttpServletResponse.SC_OK, mapOfTasks);

        } catch (SQLException e) {
            e.printStackTrace();
            handleSqlError(resp, e);
            logger.error("Guest Link: Error.", e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection c = getConnection(getServletContext())) {

        } catch (SQLException e){

        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection c = getConnection(getServletContext())) {
            DatabaseSchDao db = new DatabaseSchDao(c);
//            DatabaseTaskDao taskDb = new DatabaseTaskDao(c);
//            SchAllInfoService service = new SchAllInfoService(db, taskDb);

            int userId = Integer.parseInt(req.getParameter("userId"));
            int schId = Integer.parseInt(req.getParameter("schId"));

            Map<String,Task> mapOfTasks = service.getTasksMap(userId, schId);
            sendMessage(resp, HttpServletResponse.SC_OK, mapOfTasks);
        }
        catch (SQLException e) {
            e.printStackTrace();
            handleSqlError(resp, e);
            logger.error("Guest Link: Error.", e);
        }


    }
}

package com.codecool.web.servlet;

import com.codecool.web.dao.DatabaseSchDao;
import com.codecool.web.model.Schedule;
import com.codecool.web.service.SchService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/protected/schServlets")
public class SchServlets extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection c = getConnection(req.getServletContext())){
            String id = req.getParameter("id");
            DatabaseSchDao db = new DatabaseSchDao(c);
            SchService service = new SchService(db);

            List<Schedule> schedules = service.getSchedulesByID(id);

            resp.setContentType("application/json");
            sendMessage(resp, HttpServletResponse.SC_OK, schedules);

        } catch (SQLException e) {
            handleSqlError(resp, e);
        }
    }
}

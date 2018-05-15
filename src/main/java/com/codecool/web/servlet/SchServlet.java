package com.codecool.web.servlet;

import com.codecool.web.dao.SchDao;
import com.codecool.web.model.Schedule;
import com.codecool.web.service.SchService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/schServlet")
public class SchServlet extends AbstractServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection c = getConnection(req.getServletContext())){
            String id = req.getParameter("id");
            SchDao db = new SchDao(c);
            SchService service = new SchService(db);

            Schedule schedule = service.getScheduleByID(Integer.parseInt(id));

            resp.setContentType("application/json");
            sendMessage(resp, HttpServletResponse.SC_OK, schedule);

        } catch (SQLException e) {
            handleSqlError(resp, e);
        }
    }
}

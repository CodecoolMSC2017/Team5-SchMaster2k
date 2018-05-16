package com.codecool.web.servlet;

import com.codecool.web.dao.SchDao;
import com.codecool.web.dto.ScheduleInformationDto;
import com.codecool.web.model.Day;
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

@WebServlet("/schServlet")
public class SchServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection c = getConnection(req.getServletContext())) {
            int id = Integer.parseInt(req.getParameter("id"));
            SchDao db = new SchDao(c);
            SchService service = new SchService(db);

            Schedule schedule = service.getScheduleByID(id);
            List<Day> days = service.getScheduleDays(id);
            ScheduleInformationDto schInf = new ScheduleInformationDto(schedule, days);

            resp.setContentType("application/json");
            sendMessage(resp, HttpServletResponse.SC_OK, schInf);

        } catch (SQLException e) {
            handleSqlError(resp, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection c = getConnection(req.getServletContext())) {
            int id = Integer.parseInt(req.getParameter("userId"));
            SchDao db = new SchDao(c);
            SchService service = new SchService(db);

            int userId = Integer.parseInt(req.getParameter("userId"));
            if (req.getParameter("schTitle") != null) {
                String schTitle = req.getParameter("schTitle");
                String schContent = req.getParameter("schContent");
                db.addSchedule(schTitle, schContent, userId);
            }
            List<Schedule> schedules = db.getAllSchByUserId(userId);
            resp.setContentType("application/json");
            sendMessage(resp, HttpServletResponse.SC_OK, schedules);

        } catch (SQLException e) {
            handleSqlError(resp, e);
        }
    }
}

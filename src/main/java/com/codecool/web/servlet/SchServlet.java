package com.codecool.web.servlet;

import com.codecool.web.dao.DatabaseSchDao;
import com.codecool.web.dao.DatabaseTaskDao;

import com.codecool.web.dto.ScheduleInformationDto;
import com.codecool.web.model.Day;
import com.codecool.web.model.Schedule;
import com.codecool.web.model.User;
import com.codecool.web.service.SchService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/protected/schServlet")
public class SchServlet extends AbstractServlet {
    private static final Logger logger = Logger.getLogger(SchServlet.class);
    private User user;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection c = getConnection(req.getServletContext())) {
            int id = Integer.parseInt(req.getParameter("id"));
            DatabaseSchDao db = new DatabaseSchDao(c);
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

            DatabaseSchDao db = new DatabaseSchDao(c);
            SchService service = new SchService(db);
            user = (User) req.getSession().getAttribute("user");

            int userId = user.getId();
            if (req.getParameter("schTitle") != null) {
                String schTitle = req.getParameter("schTitle");
                String schContent = req.getParameter("schContent");
                db.addSchedule(schTitle, schContent, userId);
                logger.info(user.getName() + ": Add sch, sch name: " + schTitle);
            }else if(req.getParameter("delete")!=null){

                int schId = Integer.parseInt(req.getParameter("schId"));

                service.deleteSchedule(schId);


                List<Schedule> schedules = db.getAllSchByUserId(user.getId());
                resp.setContentType("application/json");
                sendMessage(resp, HttpServletResponse.SC_OK, schedules);


            } else {
                logger.warn(user.getName() + ": Missing sch title");
            }

            List<Schedule> schedules = db.getAllSchByUserId(userId);
            resp.setContentType("application/json");
            sendMessage(resp, HttpServletResponse.SC_OK, schedules);

        } catch (SQLException e) {
            handleSqlError(resp, e);
            logger.error(user.getName() + ": sch adding error");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            user = (User) req.getSession().getAttribute("user");
            DatabaseSchDao db = new DatabaseSchDao(connection);
            int userId = Integer.parseInt(req.getParameter("userId"));
            int schId = Integer.parseInt(req.getParameter("schId"));
            String message = req.getParameter("message");
            db.deleteSchByAdmin(schId, message, user.getName(), userId);

            logger.info(user.getName() + ": Schedule Delet done by admin for (userID): " + userId);
            resp.setStatus(HttpServletResponse.SC_OK);

        } catch (SQLException ex) {
            handleSqlError(resp, ex);
            logger.error(user.getName() + ": Error.", ex);
        }
    }
}

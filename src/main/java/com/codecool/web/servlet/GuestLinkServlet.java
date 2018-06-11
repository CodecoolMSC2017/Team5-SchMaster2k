package com.codecool.web.servlet;

import com.codecool.web.dao.DatabaseSchDao;
import com.codecool.web.dao.DatabaseTaskDao;
import com.codecool.web.dao.GuestLinkDao;
import com.codecool.web.model.Task;
import com.codecool.web.model.User;
import com.codecool.web.service.GuestLinksService;
import com.codecool.web.service.SchAllInfoService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Map;

@WebServlet("/guestLink")
public class GuestLinkServlet extends AbstractServlet {

    private static final Logger logger = Logger.getLogger(GuestLinkServlet.class);
    private User user;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection c = getConnection(getServletContext())) {
            GuestLinkDao db = new GuestLinkDao(c);
            GuestLinksService service = new GuestLinksService(db);
            String encodedUserId = req.getParameter("id");
            String encodedSchId = req.getParameter("schid");

            Base64.Decoder decoder = Base64.getDecoder();
            byte[] decodedUserId = decoder.decode(encodedUserId);
            String userId = new String(decodedUserId);
            byte[] decodedSchId = decoder.decode(encodedSchId);
            String schId = new String(decodedSchId);

            if (service.isShareLinkExist(Integer.parseInt(userId), Integer.parseInt(schId))){
                req.setAttribute("userId", userId);
                req.setAttribute("schId", schId);
                req.getRequestDispatcher("guest.jsp").forward(req, resp);
            }else {
                req.setAttribute("message", "Sorry this sch is not shared");
                req.getRequestDispatcher("guest.jsp").forward(req, resp);
            }
        }catch (SQLException e) {
            handleSqlError(resp, e);
            logger.error("Guest Link: GET.");
        }
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
            GuestLinkDao db = new GuestLinkDao(c);
            GuestLinksService service = new GuestLinksService(db);
            int userId = Integer.parseInt(req.getParameter("userId"));
            int schId = Integer.parseInt(req.getParameter("schId"));
            user = (User) req.getSession().getAttribute("user");
            service.insertGuestLink(userId, schId);
            logger.info(user.getName() + " create shareable Link");

        } catch (SQLException e){
            handleSqlError(resp, e);
            logger.error(user.getName() + "Guest Link PUT");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection c = getConnection(getServletContext())) {
            GuestLinkDao db = new GuestLinkDao(c);
            GuestLinksService service = new GuestLinksService(db);
            int userId = Integer.parseInt(req.getParameter("userId"));
            int schId = Integer.parseInt(req.getParameter("schId"));

            service.deleteGuestLink(userId, schId);
        }
        catch (SQLException e) {
            e.printStackTrace();
            handleSqlError(resp, e);
            logger.error(user.getName() + "Guest Link delete: Error.");
        }


    }
}

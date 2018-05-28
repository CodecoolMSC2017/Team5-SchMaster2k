package com.codecool.web.servlet;

import com.codecool.web.dao.DatabaseSchDao;
import com.codecool.web.model.User;
import com.codecool.web.service.SchService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/protected/taskSchNoServlet")
public class TaskSchNoServlet extends AbstractServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(Connection c = getConnection(req.getServletContext())){
            DatabaseSchDao db = new DatabaseSchDao(c);
            SchService service = new SchService(db);
            User user = (User)req.getSession().getAttribute("user");

            sendMessage(resp, HttpServletResponse.SC_OK, service.getNoOfSchTask(user.getId()));

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}

package com.codecool.web.servlet;

import com.codecool.web.dao.DatabaseUserDao;
import com.codecool.web.dao.UserDao;
import com.codecool.web.exception.InvalidUserException;
import com.codecool.web.model.User;
import com.codecool.web.service.LoginService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/loadContent")
public class LoadContentServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }


    //login
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DataSource dataSource = (DataSource) req.getServletContext().getAttribute("dataSource");

        try (Connection connection = dataSource.getConnection()) {
            ObjectMapper objectMapper = new ObjectMapper();
            UserDao userDao = new DatabaseUserDao(connection);
            LoginService loginService = new LoginService(userDao);

            User user =(User) objectMapper.readValues(req.getParameter("user"),User.class);

            resp.setContentType("application/json");
            sendMessage(resp, HttpServletResponse.SC_OK, user);




        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

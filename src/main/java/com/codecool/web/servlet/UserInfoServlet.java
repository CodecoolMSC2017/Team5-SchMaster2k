package com.codecool.web.servlet;

import com.codecool.web.dao.DatabaseUserDao;
import com.codecool.web.dao.UserDao;

import com.codecool.web.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


@WebServlet("/userinfo")
public final class UserInfoServlet extends AbstractServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            ObjectMapper objectMapper = new ObjectMapper();
            UserDao uDao = new DatabaseUserDao(connection);
            String username = req.getParameter("username");
            User user = uDao.getUserByName(username);
            resp.setContentType("application/json");
            sendMessage(resp, HttpServletResponse.SC_OK, user);
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }
}

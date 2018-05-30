package com.codecool.web.servlet;

import com.codecool.web.dao.DatabaseUserDao;
import com.codecool.web.dao.UserDao;
import com.codecool.web.exception.InvalidUserException;

import com.codecool.web.model.User;
import com.codecool.web.service.*;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


@WebServlet("/loginServlet")
public class LoginServlet extends AbstractServlet {

    private static final Logger logger = Logger.getLogger(LoginServlet.class);
    private User user;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DataSource dataSource = (DataSource) req.getServletContext().getAttribute("dataSource");

        try (Connection connection = dataSource.getConnection()) {
            UserDao userDao = new DatabaseUserDao(connection);
            LoginService loginService = new LoginService(userDao);

            String userName = req.getParameter("name_or_email");
            String passw = req.getParameter("password");

            try {
                user = loginService.getUserByName(userName,passw);
                req.getSession().setAttribute("user", user);
                req.getRequestDispatcher("protected/main.jsp").forward(req, resp);
                logger.info(user.getName() + ": Logged in.");

            }catch (InvalidUserException e){
                req.setAttribute("error", "Wrong password or user name!");
                req.getRequestDispatcher("index.jsp").forward(req, resp);
                logger.warn("Wrong password or user name.");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            handleSqlError(resp, e);
            logger.error(user.getName() + ": Login SQL Error.", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}

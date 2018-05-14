package com.codecool.web.servlet;

import com.codecool.web.dao.DatabaseUserDao;
import com.codecool.web.dao.UserDao;
import com.codecool.web.model.SubPage;
import com.codecool.web.model.User;
import com.codecool.web.service.*;
import com.codecool.web.service.UserNotRegisteredException;
import com.codecool.web.service.exceptions.NoUserRegisteredException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/loginServlet")
public class LoginServlet extends AbstractServlet {
    //logout
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }


    //login
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DataSource dataSource = (DataSource) req.getServletContext().getAttribute("dataSource");

        try (Connection connection = dataSource.getConnection()) {
            UserDao userDao = new DatabaseUserDao(connection);
            String userName = req.getParameter("username");
            String passw = req.getParameter("password");

            LoginService loginService = new LoginService(userName, passw, userDao);
            try {
                User user = loginService.fetchUser();
                req.getSession().setAttribute("user", user);
                AvailablePages ap = new AvailablePages();
                List<SubPage> availables = ap.selectPages(connection, user);

                req.setAttribute("pageList", availables);
                req.getRequestDispatcher("protected/curriculum.jsp").forward(req, resp);


            } catch (UserNotRegisteredException e) {
                req.setAttribute("error", "Wrong password or user name!");
                req.getRequestDispatcher("index.jsp").forward(req, resp);
            } catch (NoUserRegisteredException e) {
                req.setAttribute("error", "No user registered yet!");
                req.getRequestDispatcher("index.jsp").forward(req, resp);
            }


        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

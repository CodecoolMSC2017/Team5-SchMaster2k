package com.codecool.web.servlet;

import com.codecool.web.dao.DatabaseUserDao;
import com.codecool.web.dao.UserDao;
import com.codecool.web.model.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/protected/logoutServlet")
public class LogoutServlet extends AbstractServlet {

    private static final Logger logger = Logger.getLogger(LoginServlet.class);
    private User user;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DataSource dataSource = (DataSource) req.getServletContext().getAttribute("dataSource");
        try (Connection connection = dataSource.getConnection()) {
            user = (User) req.getSession().getAttribute("user");
            UserDao uDao = new DatabaseUserDao(connection);
            logger.info(user.getName() + ": Logged out.");
            uDao.changeStatus(user.getEmail(),"logout");
            req.getSession().invalidate();
            resp.sendRedirect("index.jsp");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}

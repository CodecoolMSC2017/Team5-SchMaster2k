package com.codecool.web.servlet;

import com.codecool.web.model.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/protected/logoutServlet")
public class LogoutServlet extends AbstractServlet {

    private static final Logger logger = Logger.getLogger(LoginServlet.class);
    private User user;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        user = (User) req.getSession().getAttribute("user");
        logger.info(user.getName() + ": Logged out.");
        req.getSession().invalidate();
        resp.sendRedirect("index.jsp");
    }
}

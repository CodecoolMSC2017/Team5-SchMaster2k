package com.codecool.web.servlet;

import com.codecool.web.dao.DatabaseUserDao;
import com.codecool.web.dao.UserDao;
import com.codecool.web.model.User;
import com.codecool.web.service.RegistrationService;
import org.apache.log4j.Logger;

import javax.naming.NameNotFoundException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends AbstractServlet {

    private static final Logger logger = Logger.getLogger(RegistrationServlet.class);
    private User user;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(Connection connection = getConnection(req.getServletContext())) {
            UserDao db = new DatabaseUserDao(connection);
            RegistrationService service = new RegistrationService(db);

            String name = req.getParameter("name");
            String fname = req.getParameter("first_name");
            String lname = req.getParameter("last_name");
            String pass = req.getParameter("password");
            String email = req.getParameter("email");

            if (service.checkReg(name, email)) {
                req.setAttribute("message", "This name or email already registered");
                logger.warn(user.getName() + ": Name or email already registered.");
            }
            else {
                service.createReg(name, fname, lname, pass, email);
                req.setAttribute("message", "Registration successful");
                logger.info(user.getName() + ": Registered.");
            }
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }
        catch (SQLException|NameNotFoundException e) {
            e.printStackTrace();
            logger.error(user.getName() + ": Error.", e);
        }
    }

}

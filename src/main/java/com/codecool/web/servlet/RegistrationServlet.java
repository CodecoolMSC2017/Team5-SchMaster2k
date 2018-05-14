package com.codecool.web.servlet;

import com.codecool.web.dao.RegistrationDao;
import com.codecool.web.service.RegistrationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = (Connection) req.getServletContext()) {
            RegistrationDao db = new RegistrationDao(connection);
            RegistrationService service = new RegistrationService(db);

            String name = req.getParameter("name");
            String fname = req.getParameter("first_name");
            String lname = req.getParameter("last_name");
            String email = req.getParameter("email");
            String password = req.getParameter("password");

            service.createReg(name, fname, lname, email, password);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

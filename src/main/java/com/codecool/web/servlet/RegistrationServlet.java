package com.codecool.web.servlet;

import com.codecool.web.dao.RegistrationDao;
import com.codecool.web.service.RegistrationService;
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(Connection connection = getConnection(req.getServletContext())) {
            RegistrationDao db = new RegistrationDao(connection);
            RegistrationService service = new RegistrationService(db);

            String name = req.getParameter("name");
            String fname = req.getParameter("first_name");
            String lname = req.getParameter("last_name");
            String pass = req.getParameter("password");
            String email = req.getParameter("email");

            if (service.checkReg(name, email)) {
                service.createReg(name, fname, lname, pass, email);
                req.setAttribute("message", "Registration successful");
            }
            else {
                req.setAttribute("message", "This name or email already registered");
            }
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }
        catch (SQLException|NameNotFoundException e) {
            e.printStackTrace();
        }
    }

}

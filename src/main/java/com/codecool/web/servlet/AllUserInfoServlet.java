package com.codecool.web.servlet;

import com.codecool.web.dao.DatabaseUserDao;
import com.codecool.web.service.AllUsersService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/protected/allUserInfoServlet")
public class AllUserInfoServlet extends AbstractServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(Connection c = getConnection(getServletContext())){
            DatabaseUserDao userDb = new DatabaseUserDao(c);
            AllUsersService service = new AllUsersService(userDb);

            sendMessage(resp, HttpServletResponse.SC_OK, service.getAllUser());

        } catch (SQLException e){
            e.printStackTrace();
            handleSqlError(resp, e);
        }
    }
}

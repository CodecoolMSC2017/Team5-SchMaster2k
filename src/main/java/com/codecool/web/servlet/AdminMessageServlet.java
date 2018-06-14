package com.codecool.web.servlet;

import com.codecool.web.dao.AdminMessageDao;
import com.codecool.web.model.User;
import com.codecool.web.service.AdminMessageService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/protected/adminMessage")
public class AdminMessageServlet extends AbstractServlet{

    private User user;
    private static final Logger logger = Logger.getLogger(LoginServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(Connection c = getConnection(getServletContext())){
            AdminMessageDao db = new AdminMessageDao(c);
            AdminMessageService service = new AdminMessageService(db);

            user = (User) req.getSession().getAttribute("user");
            List<List<String>> messages = service.getUserMessages(user.getId());

            resp.setContentType("application/json");
            sendMessage(resp, HttpServletResponse.SC_OK, messages);

            logger.info(user.getName() + messages);

        }catch (SQLException e){
            handleSqlError(resp, e);
            e.printStackTrace();
        }
    }
}

package com.codecool.web.servlet;

import com.codecool.web.dao.DatabaseSchDao;
import com.codecool.web.dao.DatabaseTaskDao;
import com.codecool.web.dao.DatabaseUserDao;
import com.codecool.web.dao.TaskDao;
import com.codecool.web.dto.SchTaskListDto;
import com.codecool.web.service.AllUsersService;
import com.codecool.web.service.SchService;

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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(Connection c = getConnection(getServletContext())){
            DatabaseSchDao schDb = new DatabaseSchDao(c);
            TaskDao taskDb = new DatabaseTaskDao(c);
            SchService schService = new SchService(schDb);

            String userId = req.getParameter("userId");

            SchTaskListDto schAndTaskList = new SchTaskListDto(Integer.parseInt(userId), schService.getSchedulesByID(userId), taskDb.getTasksById(Integer.parseInt(userId)));

            sendMessage(resp, HttpServletResponse.SC_OK, schAndTaskList);
        }catch (SQLException e){
            handleSqlError(resp, e);
            e.printStackTrace();
        }
    }
}

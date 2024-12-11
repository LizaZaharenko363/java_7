package com.example.java7;

import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/user-servlet")
public class UserServlet extends HttpServlet {
    @Inject
    private UserBean userManagementBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String operation = request.getParameter("op");

        if ("create".equals(operation)) {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            User newUser = userManagementBean.createUser(name, email);
            out.println("<p>User Created: " + newUser.getName() + " (" + newUser.getEmail() + ")</p>");
        } else if ("get".equals(operation)) {
            Long userId = Long.parseLong(request.getParameter("id"));
            User user = userManagementBean.getUserById(userId);
            out.println("<p>User: " + user.getName() + " (" + user.getEmail() + ")</p>");
        } else if ("update".equals(operation)) {
            Long userId = Long.parseLong(request.getParameter("id"));
            String newName = request.getParameter("newName");
            User updatedUser = userManagementBean.updateUser(userId, newName);
            out.println("<p>User Updated: " + updatedUser.getName() + "</p>");
        } else if ("delete".equals(operation)) {
            Long userId = Long.parseLong(request.getParameter("id"));
            userManagementBean.deleteUser(userId);
            out.println("<p>User Deleted.</p>");
        } else if ("list".equals(operation)) {
            List<User> activeUsers = userManagementBean.getActiveUsers();
            out.println("<h3>Active Users:</h3>");
            for (User u : activeUsers) {
                out.println("<p>" + u.getName() + " (" + u.getEmail() + ")</p>");
            }
        } else {
            out.println("<p>Invalid operation</p>");
        }
    }
}

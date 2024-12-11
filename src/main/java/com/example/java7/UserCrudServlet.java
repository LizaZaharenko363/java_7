package com.example.java7;

import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/user-crud")
public class UserCrudServlet extends HttpServlet {

    @Inject
    private UserService userService;

    @Inject
    private UserCounterBean userCounterBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        userCounterBean.userLoggedIn();

        int activeUsersCount = userCounterBean.getUserCount();
        out.println("<p>Active Users: " + activeUsersCount + "</p>");

        String operation = request.getParameter("op");
        String message = "";
        boolean success = true;

        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("    <meta charset='UTF-8'>");
        out.println("    <title>User CRUD Operations</title>");
        out.println("    <style>");
        out.println("        body { font-family: Arial, sans-serif; max-width: 800px; margin: 0 auto; padding: 20px; }");
        out.println("        .success { color: green; }");
        out.println("        .error { color: red; }");
        out.println("        table { width: 100%; border-collapse: collapse; }");
        out.println("        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }");
        out.println("        .button { display: inline-block; padding: 5px 10px; background-color: #4CAF50; color: white; text-decoration: none; }");
        out.println("    </style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>User CRUD Operations</h1>");

        try {
            switch (operation != null ? operation : "list") {
                case "create":
                    out.println("<h2>Create User</h2>");
                    out.println("<form action='user-crud' method='POST'>");
                    out.println("Name: <input type='text' name='name' required><br>");
                    out.println("Email: <input type='email' name='email' required><br>");
                    out.println("<input type='submit' name='op' value='create'>");
                    out.println("</form>");
                    break;

                default:
                    List<User> activeUsers = userService.getActiveUsers();
                    out.println("<h2>Active Users:</h2>");
                    out.println("<table>");
                    out.println("<tr><th>ID</th><th>Name</th><th>Email</th><th>Registration Date</th><th>Active</th></tr>");

                    for (User u : activeUsers) {
                        out.println("<tr>");
                        out.println("<td>" + u.getId() + "</td>");
                        out.println("<td>" + u.getName() + "</td>");
                        out.println("<td>" + u.getEmail() + "</td>");
                        out.println("<td>" + u.getRegistrationDate() + "</td>");
                        out.println("<td>" + u.getActive() + "</td>");
                        out.println("</tr>");
                    }
                    out.println("</table>");
                    break;
            }
        } catch (Exception e) {
            message = "Error: " + e.getMessage();
            success = false;
        }

        out.println("<div class='" + (success ? "success" : "error") + "'>");
        out.println("<p>" + message + "</p>");
        out.println("</div>");

        out.println("<div style='margin-top: 20px;'>");
        out.println("<a href='user-crud?op=create' class='button'>Create User</a> ");
        out.println("<a href='user-crud?op=read' class='button'>Read User</a> ");
        out.println("<a href='user-crud?op=update' class='button'>Update User</a> ");
        out.println("<a href='user-crud?op=delete' class='button'>Delete User</a> ");
        out.println("<a href='user-crud' class='button'>List Users</a>");
        out.println("</div>");

        out.println("<br><a href='index.html'>Back to Home</a>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        userCounterBean.userLoggedOut();

        String operation = request.getParameter("op");
        String message = "";
        boolean success = true;

        try {
            switch (operation) {
                case "create":
                    String name = request.getParameter("name");
                    String email = request.getParameter("email");
                    User newUser = userService.createUser(name, email);
                    message = "Created User: " + newUser.getName();
                    break;

                default:
                    message = "Invalid operation.";
                    success = false;
                    break;
            }
        } catch (Exception e) {
            message = "Error: " + e.getMessage();
            success = false;
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html><body>");
        out.println("<h2>" + (success ? "Success" : "Error") + "</h2>");
        out.println("<p>" + message + "</p>");
        out.println("<a href='user-crud'>Back to CRUD operations</a>");
        out.println("</body></html>");
    }
}

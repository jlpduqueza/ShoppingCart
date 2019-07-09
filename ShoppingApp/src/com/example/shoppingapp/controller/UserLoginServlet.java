package com.example.shoppingapp.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.shoppingapp.domain.User;
import com.example.shoppingapp.exception.DataException;
import com.example.shoppingapp.services.UserService;
import com.example.shoppingapp.services.UserServiceImpl;

@WebServlet("/Login")
public class UserLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String jspFile = "/WEB-INF/login-page.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(jspFile);

        dispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        userService = new UserServiceImpl();

        HttpSession session = request.getSession();

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        session.setAttribute("message", "");

        if ((username == null) && (password == null)) {
            session.setAttribute("message", "Please fill the username and password field.");
            response.sendRedirect("Login");

            return;
        }
        
        try {
            if (!userService.isUserByUsernamePassword(username, password)) {
                session.setAttribute("message", "Account does not exist.");
                response.sendRedirect("Login");

                return;
            }

            session = request.getSession(true);
            session.setAttribute("user", userService.getUser(username));
            
            User user = (User) session.getAttribute("user");
            
            if (userService.isAdmin(user)) {
                response.sendRedirect("Admin/AdminHome");
            } else {
                response.sendRedirect("User/UserHome");
            }
        } catch (DataException e) {
            session.setAttribute("message", "Invalid Page Access");
            response.sendRedirect("error-from-filter.jsp");
        }
    }
}

package com.example.shoppingapp.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.shoppingapp.domain.User;
import com.example.shoppingapp.exception.DataException;
import com.example.shoppingapp.services.UserService;
import com.example.shoppingapp.services.UserServiceImpl;

@WebFilter("/Admin/*")
public class AdminFilter implements Filter {
    private UserService userService;

    public AdminFilter() {
        userService = new UserServiceImpl();
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        try {
            if (userService.isAdmin(user) == false) {
                session.setAttribute("message", "Non-Admin accounts cant access this page");
                res.sendRedirect("error-from-filter.jsp");

                return;
            }
        } catch (DataException e) {
            session.setAttribute("message", "Please log in as admin");
            res.sendRedirect("error-from-filter.jsp");
        }

        chain.doFilter(request, response);
    }
}

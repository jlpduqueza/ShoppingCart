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

import com.example.shoppingapp.exception.DataException;
import com.example.shoppingapp.services.InventoryService;
import com.example.shoppingapp.services.InventoryServiceImpl;

@WebFilter("/User/UserHome")
public class UserHomeFilter implements Filter {
    private InventoryService inventoryService;

    public UserHomeFilter() {
        inventoryService = new InventoryServiceImpl();
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession();

        try {
            if (inventoryService.getInventoryListForUser().isEmpty()) {
                session.setAttribute("message", "Cannot fetch inventory");
                res.sendRedirect("error-from-filter.jsp");

                return;
            }
        } catch (DataException e) {}

        chain.doFilter(request, response);
    }
}

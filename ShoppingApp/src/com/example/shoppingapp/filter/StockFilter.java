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

@WebFilter("/Stock/*")
public class StockFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession();

        if (req.getParameter("quantity") == null) {
            session.setAttribute("message", "No quantity to process");
            res.sendRedirect("error-from-filter.jsp");

            return;
        }

        if (req.getParameter("productCode") == null) {
            session.setAttribute("message", "Product not found");
            res.sendRedirect("error-from-filter.jsp");

            return;
        }

        chain.doFilter(request, response);
    }
}

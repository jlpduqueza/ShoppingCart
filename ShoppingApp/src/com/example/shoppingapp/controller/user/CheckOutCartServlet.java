package com.example.shoppingapp.controller.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.shoppingapp.domain.User;
import com.example.shoppingapp.exception.DataException;
import com.example.shoppingapp.services.CartService;
import com.example.shoppingapp.services.CartServiceImpl;

@WebServlet("/User/CheckOutCart")
public class CheckOutCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CartService cartService;

    public CheckOutCartServlet() {
        cartService = new CartServiceImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        session.setAttribute("message", "Invalid page access");
        response.sendRedirect("error-from-filter.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        try {
            if (!cartService.isCartValidForCheckout(user)) {
                session.setAttribute("message", "Insufficient stock");
                response.sendRedirect("../User/Cart");

                return;
            }

            cartService.checkOut(user);
        } catch (DataException e) {
            session.setAttribute("message", "Invalid Page Access");
            response.sendRedirect("error-from-filter.jsp");
        }

        response.sendRedirect("/ShoppingApp/User/UserHome");
    }
}

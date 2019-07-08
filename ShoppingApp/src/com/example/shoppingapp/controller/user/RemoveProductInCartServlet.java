package com.example.shoppingapp.controller.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.shoppingapp.domain.Product;
import com.example.shoppingapp.domain.User;
import com.example.shoppingapp.exception.DataException;
import com.example.shoppingapp.services.CartService;
import com.example.shoppingapp.services.CartServiceImpl;
import com.example.shoppingapp.services.ProductService;
import com.example.shoppingapp.services.ProductServiceImpl;

@WebServlet("/User/Cart/DeleteCart")
public class RemoveProductInCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CartService cartService;
    private ProductService productService;

    public RemoveProductInCartServlet() {
        cartService = new CartServiceImpl();
        productService = new ProductServiceImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        try {
            Product product = productService.getProduct(request.getParameter("productCode"));

            cartService.deleteProductInCart(user, product);
        } catch (DataException e) {
            session.setAttribute("message", "Invalid Page Access");
            response.sendRedirect("error-from-filter.jsp");
        }

        response.sendRedirect("../UserHome");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

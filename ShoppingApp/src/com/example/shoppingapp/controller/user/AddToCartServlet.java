package com.example.shoppingapp.controller.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.shoppingapp.controller.ValidationHelper;
import com.example.shoppingapp.domain.Product;
import com.example.shoppingapp.domain.User;
import com.example.shoppingapp.exception.DataException;
import com.example.shoppingapp.services.CartService;
import com.example.shoppingapp.services.CartServiceImpl;
import com.example.shoppingapp.services.InventoryService;
import com.example.shoppingapp.services.InventoryServiceImpl;
import com.example.shoppingapp.services.ProductService;
import com.example.shoppingapp.services.ProductServiceImpl;

@WebServlet("/User/Add/AddCart")
public class AddToCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CartService cartService;
    private InventoryService inventoryService;
    private ProductService productService;
    private ValidationHelper validationHelper;

    public AddToCartServlet() {
        cartService = new CartServiceImpl();
        inventoryService = new InventoryServiceImpl();
        productService = new ProductServiceImpl();
        validationHelper = new ValidationHelper();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        session.setAttribute("message", "Invalid page access");
        response.sendRedirect("error-from-filter.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int quantityFromCart = 0;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (request.getParameter("quantityToAddInCart") == null) {
            session.setAttribute("message", "please input quantity");
            response.sendRedirect("/ShoppingApp/User/Add/ViewAdd");

            return;
        }

        String productCodeFromCart = session.getAttribute("productCode").toString();
        Product product = null;

        try {
            product = productService.getProduct(productCodeFromCart);
        } catch (DataException e1) {
            session.setAttribute("message", "Invalid Page Access");
            response.sendRedirect("error-from-filter.jsp");
        }

        if (validationHelper.isNumeric(request.getParameter("quantityToAddInCart")) == false) {
            session.setAttribute("message", "Invalid quantity input");
            response.sendRedirect("/ShoppingApp/User/Add/ViewAdd");

            return;
        }

        quantityFromCart = Integer.parseInt(request.getParameter("quantityToAddInCart"));

        try {
            if (!cartService.isValidToAdd(user, product, quantityFromCart)) {
                session.setAttribute("message", "Insufficient quantity");
                response.sendRedirect("/ShoppingApp/User/Add/ViewAdd");

                return;
            }

            if (!inventoryService.isStockSufficient(product, quantityFromCart)) {
                session.setAttribute("message", "Insufficient stock");
                response.sendRedirect("/ShoppingApp/User/Add/ViewAdd");

                return;
            }

            cartService.addProductInCart(user, product, quantityFromCart);
            response.sendRedirect("/ShoppingApp/User/UserHome");
        } catch (DataException e) {
            session.setAttribute("message", "Invalid Page Access");
            response.sendRedirect("error-from-filter.jsp");
        }
    }
}


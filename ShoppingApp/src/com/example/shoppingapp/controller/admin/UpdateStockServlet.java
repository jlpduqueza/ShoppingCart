package com.example.shoppingapp.controller.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.shoppingapp.controller.ValidationHelper;
import com.example.shoppingapp.domain.Product;
import com.example.shoppingapp.exception.DataException;
import com.example.shoppingapp.services.InventoryService;
import com.example.shoppingapp.services.InventoryServiceImpl;
import com.example.shoppingapp.services.ProductService;
import com.example.shoppingapp.services.ProductServiceImpl;

@WebServlet("/Admin/Stock/UpdateStock")
public class UpdateStockServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private InventoryService inventoryService;
    private ProductService productService;
    private ValidationHelper validationHelper;

    public UpdateStockServlet() {
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
        String productCode = request.getParameter("productCode");
        Product product = null;
        HttpSession session = request.getSession();

        try {
            product = productService.getProduct(productCode);
        } catch (DataException e) {
            session.setAttribute("message", "Invalid Page Access");
            response.sendRedirect("error-from-filter.jsp");
        }

        if (!validationHelper.isNumeric(request.getParameter("quantity"))) {
            session.setAttribute("message", "Invalid stock input");
            response.sendRedirect("/ShoppingApp/Admin/AdminHome");
        }

        int quantity = Integer.parseInt(request.getParameter("quantity"));

        try {
            inventoryService.updateProductQuantity(product, quantity);
            session.setAttribute("message", "Stock is successfully added");
        } catch (DataException e) {
            session.setAttribute("message", "Invalid Page Access");
            response.sendRedirect("error-from-filter.jsp");
        }

        response.sendRedirect("/ShoppingApp/Admin/AdminHome");
    }
}



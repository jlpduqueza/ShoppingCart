package com.example.shoppingapp.controller.admin;

import java.io.IOException;

import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.shoppingapp.domain.InventoryEntry;
import com.example.shoppingapp.exception.DataException;
import com.example.shoppingapp.services.InventoryService;
import com.example.shoppingapp.services.InventoryServiceImpl;

@WebServlet("/Admin/Stock/EditStock")
public class EditStockServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private InventoryService inventoryService;

    public EditStockServlet() {
        inventoryService = new InventoryServiceImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        if ((request.getParameter("productCode") == null) && (session.getAttribute("productCode") == null)) {
            session.setAttribute("message", "Invalid page access");
            response.sendRedirect("error-from-filter.jsp");
        }

        String productCode = "";

        if (request.getParameter("productCode") != null) {
            productCode = request.getParameter("productCode");
            session.setAttribute("productCode", productCode);
        } else if (session.getAttribute("productCode") != null) {
            productCode = session.getAttribute("productCode").toString();
        }

        try {
            InventoryEntry inventoryEntry = inventoryService.getInventory(productCode);

            if (inventoryEntry == null) {
                session.setAttribute("message", "Invalid page access");

                return;
            }

            BigDecimal price = inventoryEntry.getProduct().getPrice();
            int quantityFromStock = inventoryEntry.getQuantity();
            String productName = inventoryEntry.getProduct().getProductDescription();

            request.setAttribute("productCode", productCode);
            request.setAttribute("quantityFromStock", quantityFromStock);
            request.setAttribute("price", price);
            request.setAttribute("productName", productName);
            request.getRequestDispatcher("/WEB-INF/edit-stock.jsp").forward(request, response);
        } catch (DataException e) {
            session.setAttribute("message", "Invalid Page Access");
            response.sendRedirect("error-from-filter.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

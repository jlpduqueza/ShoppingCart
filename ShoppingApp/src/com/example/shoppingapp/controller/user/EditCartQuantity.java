package com.example.shoppingapp.controller.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.shoppingapp.domain.CartEntry;
import com.example.shoppingapp.domain.Product;
import com.example.shoppingapp.domain.User;
import com.example.shoppingapp.exception.DataException;
import com.example.shoppingapp.services.CartService;
import com.example.shoppingapp.services.CartServiceImpl;
import com.example.shoppingapp.services.InventoryService;
import com.example.shoppingapp.services.InventoryServiceImpl;
import com.example.shoppingapp.services.ProductService;
import com.example.shoppingapp.services.ProductServiceImpl;

@WebServlet("/User/Cart/EditCartQuantity")
public class EditCartQuantity extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	private CartService cartService;
	private ProductService productService;
	private InventoryService inventoryService;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		cartService = new CartServiceImpl();
		productService = new ProductServiceImpl();
		inventoryService = new InventoryServiceImpl();
		
	    HttpSession session=request.getSession();
    	User user = (User) session.getAttribute("user");

	    String productCode = "";
	    
    	if(request.getParameter("productCode") != null) {
    		productCode = request.getParameter("productCode");
    		session.setAttribute("productCode", productCode);
	    }
	    
	    else if(session.getAttribute("productCode") != null) {
	    	productCode = session.getAttribute("productCode").toString();
	    	
	    }
    	
    	try {
    		Product product = productService.getProduct(productCode);
    		
	    	CartEntry cartEntry = cartService.getCartEntry(user, product);
	    	
	    	if(cartEntry == null) {
	    		
	    		session.setAttribute("message", "Invalid page access");
				return;
	    	}
	    	
			int quantityFromStock = inventoryService.getInventory(productCode).getQuantity();
			
			request.setAttribute("quantityFromStock", quantityFromStock);
			
			request.getRequestDispatcher("/WEB-INF/edit-cart-quantity.jsp").forward(request,response);
			
		} catch (DataException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}

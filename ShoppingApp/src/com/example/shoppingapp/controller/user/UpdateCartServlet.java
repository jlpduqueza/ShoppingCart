package com.example.shoppingapp.controller.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
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

@WebServlet("/User/Cart/UpdateCart")
public class UpdateCartServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private CartService cartService;				
	private InventoryService inventoryService;
	private ProductService productService;
	private ValidationHelper validationHelper;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{

		HttpSession session = request.getSession();
		
		session.setAttribute("message", "Invalid page access");
		response.sendRedirect("error-from-filter.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		cartService = new CartServiceImpl();
		inventoryService = new InventoryServiceImpl();
		productService = new ProductServiceImpl();
		validationHelper = new ValidationHelper();
		
		
		System.out.println(request.getParameter("productCode"));
		
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("user");
	
		Product product = null;
		
		try {
			product = productService.getProduct(request.getParameter("productCode"));
		} catch (DataException e1) {

			e1.printStackTrace();
		}
		
		int quantity = 0;
		if(validationHelper.isNumeric(request.getParameter("quantity")) == false) {
			
    	    session.setAttribute("message", "Invalid quantity input");
    		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/CartServlet");
    		dispatcher.forward(request,response);
		}
			
		quantity = Integer.parseInt(request.getParameter("quantity")) ;
			
		try {

			if(!inventoryService.isStockSufficient(product, quantity)) {
				session=request.getSession();
				session.setAttribute("message", "Insufficient stock");
	    	    response.sendRedirect("../User/Cart");
				return;
			}
			cartService.updateProductInCart(user, product, quantity);
			
		} catch (NumberFormatException e) {

			e.printStackTrace();
			
		} catch (DataException e) {

			e.printStackTrace();
		}
			
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/User/Cart");
		dispatcher.forward(request,response);
			
	}

}

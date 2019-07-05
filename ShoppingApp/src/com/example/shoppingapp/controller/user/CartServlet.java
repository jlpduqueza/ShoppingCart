package com.example.shoppingapp.controller.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.shoppingapp.domain.User;
import com.example.shoppingapp.exception.DataException;
import com.example.shoppingapp.services.CartProductInventoryService;
import com.example.shoppingapp.services.CartProductInventoryServiceImpl;
import com.example.shoppingapp.services.CartService;
import com.example.shoppingapp.services.CartServiceImpl;

@WebServlet("/User/Cart")
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private CartProductInventoryService cartProductService;
	private CartService cartService;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
	    cartProductService = new CartProductInventoryServiceImpl();
	    cartService = new CartServiceImpl();
	    
    	HttpSession session=request.getSession();
    	
    	User user = (User) session.getAttribute("user");
	    
	    try {
	    	
			if(cartService.getCartList(user) == null || cartService.getCartList(user).size() == 0) {
	    	    session.setAttribute("message", "Your cart is empty.");
	    	    response.sendRedirect("../User/UserHome");
				return;
			}
			request.setAttribute("product_collection", cartProductService.getBeanList(user));
			request.setAttribute("totalPrice", cartProductService.getTotalPrice(user));
			
		} catch (DataException e) {

			e.printStackTrace();
		}

	    String jspFile = "/WEB-INF/cart.jsp";
	    
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(jspFile);
        dispatcher.forward(request,response);	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		doGet(request, response);
	}

}

package com.example.shoppingapp.controller.admin;

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
import com.example.shoppingapp.services.InventoryService;
import com.example.shoppingapp.services.InventoryServiceImpl;

@WebServlet("/Admin/AdminHome")
public class AdminHomePageServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	private InventoryService inventoryService;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
	  
		inventoryService = new InventoryServiceImpl();
		
		try {
			request.setAttribute("product_collection", inventoryService.getInventoryList());
		} catch ( DataException e) {

			e.printStackTrace();
		}
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		request.setAttribute("username", user.getUsername());
	   
		String jspFile = "/WEB-INF/admin-home.jsp";
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(jspFile);
		dispatcher.forward(request,response);	
	   
	}
	   
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		   
		doGet(request, response);
	}

	
}

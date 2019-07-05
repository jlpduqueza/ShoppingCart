package com.example.shoppingapp.controller;

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
import com.example.shoppingapp.services.UserService;
import com.example.shoppingapp.services.UserServiceImpl;

@WebServlet("/Login")
public class UserLoginServlet extends HttpServlet{
	   
	private static final long serialVersionUID = 1L;
	private UserService userService;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String jspFile = "/WEB-INF/login-page.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(jspFile);
		dispatcher.forward(request,response);
	}
	   
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		   userService = new UserServiceImpl();
		   
		   HttpSession session=request.getSession(true);
		   session.setAttribute("message", "");
		   
		   if(request.getParameter("username") == null && request.getParameter("password") == null) {
	    	   session.setAttribute("message", "Please fill the username and password field.");
	    	   response.sendRedirect("Login");
			   return;
		   }
		   if(request.getParameter("username").matches("[^a-zA-Z0-9]")) {
	    	   session.setAttribute("message", "Username required alphanumeric input.");
	    	   response.sendRedirect("Login");
			   return;
		   }
		   
		   String username = request.getParameter("username");
	       String password = request.getParameter("password");
	       
	       User tempUser = new User();
	       
	       tempUser.setUsername(username);
	       tempUser.setPassword(password);
	       
	       try {
	    	   
	    	   if(userService.isUser(tempUser) == false) {
				   session.setAttribute("message", "Account does not exist.");
				   response.sendRedirect("Login");
				   return;
	    	   }
	    	   
			   session=request.getSession(true);
			   session.setAttribute("user", userService.getUser(username));
	    	   
			   if(userService.isAdmin(tempUser)) {
				   response.sendRedirect("Admin/AdminHome");
			   } else {
				   response.sendRedirect("User/UserHome");
			   }
				
	       } catch (DataException e2) {

				e2.printStackTrace();
	       }
		}
	}

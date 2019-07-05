package com.example.shoppingapp.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.shoppingapp.domain.User;
import com.example.shoppingapp.exception.DataException;
import com.example.shoppingapp.services.UserService;
import com.example.shoppingapp.services.UserServiceImpl;

@WebFilter("/User/*")
public class UserFilter implements Filter {

	private UserService userService;
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		userService = new UserServiceImpl();
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session=req.getSession();
		
		User user = (User) session.getAttribute("user");
		
		try {

			if(userService.isAdmin(user)) {
				
				session.setAttribute("message", "Please log-in as user");
				res.sendRedirect("error-from-filter.jsp");
				return;
			}
			
		} catch (DataException e) {
			
			session.setAttribute("message", "Page not found.");
			res.sendRedirect("error-from-filter.jsp");
			return;
		}
		
		chain.doFilter(request, response);
	}

}

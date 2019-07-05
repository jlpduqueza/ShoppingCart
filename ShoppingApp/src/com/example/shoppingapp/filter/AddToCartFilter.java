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

@WebFilter("/Add/*")
public class AddToCartFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session=req.getSession();
		
	    if(req.getParameter("productCode") == null && session.getAttribute("productCode") == null) {
			
			session.setAttribute("message", "Product cannot be processed");
			res.sendRedirect("error-from-filter.jsp");
			return;
	    }
		chain.doFilter(request, response);
	}

}

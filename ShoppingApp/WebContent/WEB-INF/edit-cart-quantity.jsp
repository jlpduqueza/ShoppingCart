<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII" isErrorPage="true"%>
<%@ page errorPage = "error.jsp" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8">
      <title>Insert title here</title>
   </head>
   <body>
      <h3><c:out value = "${productName}"/></h3>
      <c:out value = "${message}"/>
      <form action="UpdateCart" method = "POST">
         <input type="number" min="0" name="quantity" value ="${quantityFromCart}" >
         <input type="hidden" name="productCode" value = "${productCode}">
         <input type="submit" value = "Update" >
      </form>
      <br>
      <c:set var="message" value="" scope="session"/>
      <br>
      <a href="/ShoppingApp/User/Cart">Go back</a>
   </body>
</html>
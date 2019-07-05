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
      <form action="AddCart" method = "POST">
         Quantity (between 1 and ${ quantity}): 
         <input type="number" min="1" max="${quantity}" name="quantityToAddInCart" oninput="(!validity.rangeOverflow||(value=${quantity})) && (!validity.rangeUnderflow||(value=1)) &&(!validity.stepMismatch||(value=parseInt(this.value)));">
         <input type="hidden" name="username" value = "${username}">
         <input type="hidden" name="quantity" value = "${quantity}">
         <input type="hidden" name="productCode" value = "${productCode}">
         <input type="hidden" name="quantityFromStock" value = "${quantity}">
         <input type="hidden" name="price" value = "${price}">
         <input type="hidden" name="productName" value = "${productName}">
         <input type="submit" value = "add to cart" >
      </form>
      <br>
      <c:set var="message" value="" scope="session"/>
      <br>
      <a href="/ShoppingApp/User/UserHome">Go back shopping</a>
   </body>
</html>
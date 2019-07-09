<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8">
      <title>Insert title here</title>
   </head>
   <body>
      <h1>Hello <c:out value = "${username}"/>!</h1>
      <a href="Cart">CART</a>
      <table id="example">
         <tr>
            <th>Products</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>Action</th>
            <c:forEach var="inventoryProduct" items="${product_collection}">
         <tr>
         <td><c:out value = "${inventoryProduct.product.productDescription}"/></td>
         <td>&#8369;<c:out value = "${inventoryProduct.product.price}"/></td>
         <td><c:out value = "${inventoryProduct.quantity}"/></td>
         <td>	
         <a href="/ShoppingApp/User/Add/ViewAdd?productCode=${inventoryProduct.product.productCode}">Add To Cart</a>
         </td>	
         </tr>
         </c:forEach>
         <c:out value = "${message}"/>
         <c:set var="message" value="" scope="session"/>
      </table>
      <a href="../Logout" id="test">Log-out</a>
      <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js">
         $(document).ready(function() {
         
            $('#example tr').click(function() {
                var href = $(this).find("a").attr("href");
                if(href) {
                    window.location = href;
                }
            });
         
         });
         
      </script>
   </body>
</html>
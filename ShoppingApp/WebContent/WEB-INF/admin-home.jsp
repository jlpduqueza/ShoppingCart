<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8">
      <title>Insert title here</title>
   </head>
   <body>
      <h1>Hello ${username}!</h1>
      <c:out value = "${message}"/>
      <table id="example">
         <tr>
            <th>Products</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>Action</th>
            <c:forEach var="inventoryProduct" items="${product_collection}">
         <tr>
         <td>
         ${inventoryProduct.product.productDescription}
         </td>
         <td>
         &#8369;${inventoryProduct.product.price}
         </td>
         <td>	
         ${inventoryProduct.quantity}
         <td>
         <a href="/ShoppingApp/Admin/Stock/EditStock?productCode=${inventoryProduct.product.productCode}">Edit stock</a>					
         </td>
         </tr>
         </c:forEach>
      </table>
      <c:set var="message" value="" scope="session"/>
      <a href="../Logout" id="test">Log-out</a>
   </body>
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
</html>
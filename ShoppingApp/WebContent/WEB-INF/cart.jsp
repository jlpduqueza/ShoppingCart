<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8">
      <title>Cart</title>
   </head>
   <body>
      <table>
         <tr>
            <th>Products</th>
            <th>Price</th>
            <th>Available Stock</th>
            <th>Quantity</th>
            <th>Action</th>
            <c:forEach var="cartProduct" items="${product_collection}">
         <tr>
         <td>
         <c:out value = "${cartProduct.product.productDescription}"/>
         </td>
         <td>
         &#8369; <c:out value = "${cartProduct.product.price}"/>
         </td>
         <td>
         <c:out value = "${cartProduct.inventoryEntry.quantity}"/>
         <td>
         <c:out value = "${cartProduct.cartEntry.quantityInCart}"/>
         </td>
         <td>	
         <a href="/ShoppingApp/User/Cart/DeleteCart?productCode=${cartProduct.product.productCode}">Delete</a> 
         <a href="/ShoppingApp/User/Cart/EditCartQuantity?productCode=${cartProduct.product.productCode}">Edit</a> 
         </td>	
         </c:forEach>
         <tfoot>
            <tr>
               <td>Total Price :${totalPrice} </td>
            </tr>
         </tfoot>
      </table>
      <form action="CheckOutCart" method = "POST">
         <input type="Submit" value = "Check Out" >
      </form>
      <br>
      <c:out value = "${message}"/>
      <c:set var="message" value="" scope="session"/>
      <br>
      <a href="/ShoppingApp/User/UserHome">Go back shopping</a> 
   </body>
</html>
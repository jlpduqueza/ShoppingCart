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
         ${cartProduct.product.productDescription}
         </td>
         <td>
         &#8369; ${cartProduct.product.price}
         </td>
         <td>
         ${cartProduct.inventoryEntry.quantity}
         <td>
         ${cartProduct.cartEntry.quantityInCart}
         </td>
<%--          <td>
          <input type="number" name="quantity" value = "<%=product.getQuantity()%>" min="1" max="<%=cartService.getActualQuantity(product.getProduct_code())%>">
         
         </td> --%>
<%--          <td>
         <input type="hidden" name="username" value = " ${username}">
         <input type="hidden" name="productCode" value = "${cartProduct.product.productCode}">
         <input type="hidden" name="quantity" value = " ${cartProduct.cartEntry.quantityInCart}">
         <input type="hidden" name="initialPrice" value = "${initialPrice}">
         <input type="Submit" value = "Save" >
         </td>
         </form>  --%>
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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8">
      <title>Insert title here</title>
   </head>
   <body>
      <form action="Login" method = "POST">
         Username:<br>
         <input type="text" name="username">
         <br>
         Password:<br>
         <input type="password" name="password" >
         <br><br>
         <input type="submit" value = "Login">
      </form>
      <c:out value = "${message}"/>
      <c:set var="message" value="" scope="session"/>
   </body>
</html>
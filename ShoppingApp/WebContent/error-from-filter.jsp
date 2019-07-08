<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8">
      <title>Insert title here</title>
   </head>
   <body>
      ${message}
      <c:set var="message" value="" scope="session"/>
      <br>
      <button onclick="goBack()">Go to previous page</button>
      <script>
         function goBack() {
           window.history.back();
         }
      </script>
   </body>
</html>
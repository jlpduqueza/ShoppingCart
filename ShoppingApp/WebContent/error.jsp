<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ page language="java" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h4>Page cannot be accessed</h4>
	<button onclick="goBack()">Go to previous page</button>

	<script>
	function goBack() {
	  window.history.back();
	}
	</script>
</body>
</html>

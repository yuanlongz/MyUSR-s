<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>LogIn to MyUSR</title>
</head>
<body>
	<h1>LogIn to MyUSR</h1>
	<form action="LoginControllerServlet" method="post">
		<%
			if (request.getAttribute("errorMessage") != null) {
		%>
		<h5 style="color: red"><%=request.getAttribute("errorMessage")%></h5>
		<%
			}
		%>
		Email   : <input type="email" name="account" required><br>
		PassWord: <input type="password" name="passWord" required>
		<br> <input type="submit" value="Login">
	</form>
</body>
</html>
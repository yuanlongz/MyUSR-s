<%@page import="security.AppSession"%>
<%@page import="domain.User"%>
<%@page import="domain.Role"%>
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
	<!-- 	display login error message -->
	<%
		if (request.getAttribute("errorMessage") != null) {
	%>
	<h5 style="color: red"><%=request.getAttribute("errorMessage")%></h5>
	<%
		}
	%>
	<form action="LoginControllerServlet" method="post">


		<!-- 		login crediential check -->
		<%
			if (!AppSession.isAuthenticated()) {
		%>
		Email : <input type="email" name="account" required><br>
		PassWord: <input type="password" name="passWord" required> <br>
		<input type="submit" value="Login">
	</form>
	<%
		} else {
			String redirect = null;
			User user = AppSession.getUser();
			if (user.getRole().equals(Role.ADMIN)) {
				redirect = "adminHome";
			} else if (user.getRole().equals(Role.CUSTOMER)) {
				redirect = "customerHome";
			}
	%>
	You are already logged in as<%=user.getName()%>.
	<div class='container'>
		<a href=<%=redirect%>>GO DashBoard</a>
	</div>

	<%
		}
	%>
</body>
</html>
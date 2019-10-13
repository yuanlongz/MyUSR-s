<%@page import="domain.Customer"%>
<%@page import="domain.User"%>
<%@page import="domain.Admin"%>
<%@page import="domain.Service"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
		//allow access only if session exists
		String user = null;
		if (session.getAttribute("user") == null) {
			response.sendRedirect("login.jsp");
		} else
			user = (String) session.getAttribute("user");
		String userName = null;
		String sessionID = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("user"))
					userName = cookie.getValue();
				if (cookie.getName().equals("JSESSIONID"))
					sessionID = cookie.getValue();
			}
		}
	%>
	<h3>
		Hi
		<%=userName%>, Login successful. Your Session ID=<%=sessionID%></h3>

	<form action="LogoutControllerServlet" method="post">
		<input style="position: absolute; top: 10px; right: 10px;"
			type="submit" value="Logout">
	</form>
</body>
</html>
<%@page import="session.Session"%>
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
		//TODO: session update security
		String userName = null;	
		if(Session.checkSession(request, response)){
			String userId = Session.getUserId(session);
			userName = User.getUserById(userId).getName();
		}
		
	%>
	<h3>
		Hi
		<%=userName%>, Login successful. Your Session ID=<%=Session.getSessionId(request)%></h3>

	<form action="LogoutControllerServlet" method="post">
		<input style="position: absolute; top: 10px; right: 10px;"
			type="submit" value="Logout">
	</form>
	
<!-- 	will this carry session?  -->
	<a href="profile.jsp"><img
		src="https://www.profilegroup.co.nz/wp-content/uploads/2015/12/au.png"
		style="width: 82px; height: 86px" title="White flower" alt="Flower"></a>
</body>
</html>
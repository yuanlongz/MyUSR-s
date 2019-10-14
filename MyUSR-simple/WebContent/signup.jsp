<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<!-- back button -->
	<input type="button" onclick="location.href='index.html'" ; value="Back"
		style="position: absolute; top: 10px; left: 0px;" />
	<br><h1>MyUSR SignUp Form</h1>
	<form action="SignupControllerServlet" method=post>

		Your Name: <input type="text" name=name required><br>
		Your EmailAddress(Account): <input type="email" name=account required><br>
		Your Password:<input type="password" name=password required><br>

		<input type="submit" value="Submit">
	</form>
</body>
</html>
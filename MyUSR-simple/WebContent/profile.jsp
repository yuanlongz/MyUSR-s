<%@page import="domain.Role"%>
<%@page import="domain.User"%>
<%@page import="session.Session"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Profile</title>
<style>
/* profile card */
.card {
	box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
	max-width: 300px;
	margin: auto;
	text-align: center;
	font-family: arial;
}

button {
	border: none;
	outline: 0;
	display: inline-block;
	padding: 8px;
	color: white;
	background-color: #000;
	text-align: center;
	cursor: pointer;
	width: 100%;
	font-size: 18px;
}

a {
	text-decoration: none;
	font-size: 22px;
	color: black;
}

button:hover, a:hover {
	opacity: 0.7;
}
/* pop up form */
body {
	font-family: Arial, Helvetica, sans-serif;
}

* {
	box-sizing: border-box;
}

/* The popup form - hidden by default */
.form-popup {
	display: none;
	position: fixed;
	bottom: 0;
	right: 15px;
	border: 3px solid #f1f1f1;
	z-index: 9;
}

/* Add styles to the form container */
.form-container {
	max-width: 300px;
	padding: 10px;
	background-color: white;
}

/* Full-width input fields */
.form-container input[type=text], .form-container input[type=password] {
	width: 100%;
	padding: 15px;
	margin: 5px 0 22px 0;
	border: none;
	background: #f1f1f1;
}

/* When the inputs get focus, do something */
.form-container input[type=text]:focus, .form-container input[type=password]:focus
	{
	background-color: #ddd;
	outline: none;
}

/* Set a style for the submit/login button */
.form-container .btn {
	background-color: #4CAF50;
	color: white;
	padding: 16px 20px;
	border: none;
	cursor: pointer;
	width: 100%;
	margin-bottom: 10px;
	opacity: 0.8;
}

/* Add a red background color to the cancel button */
.form-container .cancel {
	background-color: red;
}

/* Add some hover effects to buttons */
.form-container .btn:hover, .open-button:hover {
	opacity: 1;
}
</style>
</head>
<body>
	<%
		//check session
		User user = null;
		String pic = null;
		String name = null;
		String account = null;
		String psw = null;
		Role role = null;
		String backLink = null;
		if (Session.checkSession(request, response)) {
			user = User.getUserById(Session.getUserId(request));
			//choose profile pic
			if (user.getRole().equals(Role.ADMIN)) {
				pic = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQY_7DqY7TbUBxG8Qo4U_3UCK0Z3lX7EwNJcIvBr3fp_h1I4clNNQ";
			} else if (user.getRole().equals(Role.CUSTOMER)) {
				pic = "data:image/jpeg;base64,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";
			}
			name = user.getName();
			account = user.getAccount();
			psw = user.getPassword();
			role = user.getRole();
		}
		
		if (role.equals(Role.ADMIN)){
			backLink = "adminHome.jsp";
		}else if(role.equals(Role.CUSTOMER)){
			backLink = "customerHome.jsp";
		}else {
			backLink = "login.jsp";
		}
	%>
	<!-- back button -->
	<input type="button" onclick="location.href='<%=backLink %>'" ; value="Back"
		style="position: absolute; top: 10px; left: 0px;" />
		
	<!-- display profile -->
	<h2 style="text-align: center">User Profile Card</h2>
	<!-- 	update render info -->
	<%
		if (request.getAttribute("errorMessage") != null) {
	%>
	<h5 style="color: blue;"><%=request.getAttribute("errorMessage")%></h5>
	<%
		}
	%>
	<div class="card">
		<img src=<%=pic%> style="width: 100%">
		<h1><%=name%></h1>
		<p>
			Account:
			<%=account%></p>
		Password: <input type="password" value=<%=psw%> id="myInput"><br>
		<br> <input type="checkbox" onclick="myFunction()">Show
		Password

		<script>
			function myFunction() {
				var x = document.getElementById("myInput");
				if (x.type === "password") {
					x.type = "text";
				} else {
					x.type = "password";
				}
			}
		</script>
		<!-- edit Profile -->
		<p>
			<button onclick="openForm()">Edit</button>
		</p>
		<div class="form-popup" id="myForm">
			<form action="ProfileControllerServlet" class="form-container"
				method="post">
				<h1>Edit Profile</h1>

				<label for="name"><b>Name</b></label> <input type="text"
					placeholder="Enter User Name" name="name" required> <label
					for="psw"><b>Password</b></label> <input type="password"
					placeholder="Enter Password" name="psw" required>

				<button type="submit" class="btn">Update</button>
				<button type="button" class="btn cancel" onclick="closeForm()">Close</button>
			</form>
		</div>
		<script>
			function openForm() {
				document.getElementById("myForm").style.display = "block";
			}

			function closeForm() {
				document.getElementById("myForm").style.display = "none";
				onclick = window.location.reload();
			}
		</script>
	</div>

</body>
</html>
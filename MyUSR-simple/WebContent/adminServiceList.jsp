<%@page import="domain.ServiceStatus"%>
<%@page import="java.util.ArrayList"%>
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
<title>Manage Service List</title>
<style type="text/css">
/* service table */
.tg {
	border-collapse: collapse;
	border-spacing: 0;
	border-color: #aabcfe;
}

.tg td {
	font-family: Arial, sans-serif;
	font-size: 14px;
	padding: 10px 5px;
	border-style: solid;
	border-width: 1px;
	overflow: hidden;
	word-break: normal;
	border-color: #aabcfe;
	color: #669;
	background-color: #e8edff;
}

.tg th {
	font-family: Arial, sans-serif;
	font-size: 14px;
	font-weight: normal;
	padding: 10px 5px;
	border-style: solid;
	border-width: 1px;
	overflow: hidden;
	word-break: normal;
	border-color: #aabcfe;
	color: #039;
	background-color: #b9c9fe;
}

.tg .tg-hmp3 {
	background-color: #D2E4FC;
	text-align: left;
	vertical-align: top
}

.tg .tg-baqh {
	text-align: center;
	vertical-align: top
}

.tg .tg-mb3i {
	background-color: #D2E4FC;
	text-align: right;
	vertical-align: top
}

.tg .tg-lqy6 {
	text-align: right;
	vertical-align: top
}

.tg .tg-0lax {
	text-align: left;
	vertical-align: top
}

/* pop up form */
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
		//TODO:get session info
		Admin user = null;
		ArrayList<Service> serviceList = new ArrayList<Service>();
		if (Session.checkSession(request, response)) {
			user = (Admin) User.getUserById(Session.getUserId(session));
			//serviceList = user.getServiceList();
		}
	%>
	<h2>Service List</h2>
	<hr />
	<br />
	<div>
	<form>
	<input type="radio" name="view" value="All" checked > All<br>
	<input type="radio" name="view" value="Ongoing"> Ongoing<br>
  	<input type="radio" name="view" value="Completed"> Completed<br>
  	<input type="submit" value="Refresh">
  	</form>
  
	</div>
	<div class='container'>
		<table class="tg">
			<tr>
				<th class="tg-baqh" colspan="7">Summary</th>
			</tr>
			<tr>
				<th class="tg-hmp3">Service id</th>
				<th class="tg-hmp3">Customer</th>
				<th class="tg-hmp3">Item list</th>
				<th class="tg-hmp3">Service description</th>
				<th class="tg-hmp3">Bills</th>
				<th class="tg-hmp3">Service status</th>
				<th class="tg-hmp3">Action</th>
			</tr>
			<%
			String option = request.getParameter("view");
			if(option != null &&option.equals("Ongoing")) {
				serviceList = user.getServiceList();
			}else if(option != null && option.equals("Completed")) {
				serviceList = Service.findByStatus(ServiceStatus.COMPLETE);
			}else {
				serviceList = Service.findAll();
			}
				for (Service service : serviceList) {
					//record service_id
					session.setAttribute(Session.Target_ATTRIBUTE_ID, service.getServiceID());
			%>
			<tr>
				<td class="tg-0lax"><%=service.getServiceID()%></td>
				<td class="tg-0lax"><%=User.getUserById(service.getUserID()).getName()%></td>
				<td class="tg-lqy6"><%=service.getItemStringList()%></td>
				<td class="tg-lqy6"><%=service.getDescription()%></td>
				<td class="tg-lqy6"><%=service.getBill()%></td>
				<td class="tg-lqy6"><%=service.getStatus().name()%></td>
				<td class="tg-lqy6" align="left"><select>
						<option onclick="location.href =
					'serviceDetails.jsp';">More</option>
						<option onclick="openForm()">Edit</option>
				</select></td>
			</tr>
			<%
				}
			%>
		</table>
	</div>
	<div class="form-popup" id="myForm">
		<form action="ServiceControllerServlet" class="form-container"
			method="post">
			<h1>Change Service Status</h1>

			<label for="name"><b>Service Status</b></label> <select id="isStatus"
				name="isStatus">
				<%
					for (ServiceStatus status : ServiceStatus.values()) {
				%>
				<option value=<%=status.name()%> name=status><%=status.name()%></option>
				<%
					}
				%>
			</select>

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
			window.location.reload(true);
		}
	</script>
</body>
</html>
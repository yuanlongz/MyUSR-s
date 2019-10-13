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
</style>
</head>
<body>
	<%
		//TODO:get session info
		String userName = "admin@usr.au";
	%>
	<h2>Service List</h2>
	<hr />
	<br />
	<div class='container'>
		<table class="tg">
			<tr>
				<th class="tg-baqh" colspan="6">Summary</th>
			</tr>
			<tr>
				<th class="tg-hmp3">Service id</th>
				<th class="tg-hmp3">Customer</th>
				<th class="tg-hmp3">Item list</th>
				<th class="tg-hmp3">Service description</th>
				<th class="tg-hmp3">Service status</th>
				<th class="tg-hmp3">Action </th>
			</tr>
			<%
				Admin admin = (Admin) User.getUserByAccount(userName);
				for (Service service : admin.getServiceList()) {
			%>
			<tr>
				<form action="ServiceControllerServlet" method="post">
					<input type="hidden" name="serviceId"
						value="<%=service.getServiceID()%>">
					<td class="tg-0lax"><%=service.getServiceID()%></td>
					<td class="tg-0lax"><%=User.getUserById(service.getUserID()).getName()%></td>
					<td class="tg-lqy6"><%=service.getItemStringList()%></td>
					<td class="tg-lqy6"><%=service.getDescription()%></td>
					<td class="tg-lqy6"><%=service.getStatus().name()%></td>
					<td class="tg-lqy6" align="left"><input type="submit"
						value="Edit"></td>
				</form>
			</tr>
			<%
				}
			%>
		</table>
	</div>
</body>
</html>
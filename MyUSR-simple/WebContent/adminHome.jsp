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
<style>
* {
	box-sizing: border-box;
}

.column {
	float: left;
	width: 33.33%;
	padding: 5px;
}

/* Clearfix (clear floats) */
.row::after {
	content: "";
	clear: both;
	display: table;
}
</style>
</head>
<body>
	<%
		//allow access only if session exists
		//TODO: session update security
		String userName = null;
		if (Session.checkSession(request, response)) {
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

	<div class="row">
		<!-- My	profile -->
		<div class="column">
			<a href="profile.jsp"><img
				src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAMgAAADICAMAAACahl6sAAAAM1BMVEUKME7///+El6bw8vQZPVlHZHpmfpHCy9Ojsbzg5ekpSmTR2N44V29XcYayvsd2i5yTpLFbvRYnAAAJcklEQVR4nO2d17arOgxFs+kkofz/154Qmg0uKsuQccddT/vhnOCJLclFMo+//4gedzcApf9B4srrusk+GsqPpj+ypq7zVE9LAdLWWVU+Hx69y2FMwAMGyfusLHwIpooyw9IAQfK+8naDp3OGHvZ0FMhrfPMgVnVjC2kABOQ1MLvi0DEIFj1ILu0LU2WjNRgtSF3pKb4qqtd9IHmjGlJHlc09IHlGcrQcPeUjTAySAGNSkQlRhCCJMGaUC0HSYUx6SmxFAtJDTdylsr4ApC1TY0yquKbCBkk7qnYVzPHFBHkBojhVJWviwgPJrsP4qBgTgbQXdsesjm4pDJDmIuswVZDdFx0ENTtkihoeqSDXD6tVxOFFBHndMKxWvUnzexpIcx/Gg2goJJDhVo6PCMGRAnKTmZuKm3wcJO/upphUqUHy29yVrRhJDORXOKIkEZDf4YiRhEF+iSNCEgb5KY4wSRDkB/yurUEG8nMcocgYABnvbrVL3nMIP0h/d5udKnwzSC/InfPdkJ6eWb0PJE++dyVVyQP5iQmWW27X5QG5druEKafBu0Hqu9saVOHa8HKC/K6BzHKZiRMEZCDF0Nd1/ZfXI/fcOibHOssFgokg9uFA20BhztHEAZIjIohrD/o1wljeFBDEwBo8YUt5Ir/rNLjOIACPFdy/AbEcPdcJBOCxytjeYAM4Kzp6rhOIPhRGNzwmFP3rOoTFI0irtnQKx6fj1Zt+h9njEUS9mKJxfFRrX5lt7wcQtaWTOfTHeIXVJQcQrRW+OYex2j0a66XZINoO8a7fPH2iHF2mC7ZBtB3Czb5QvjizSx7A3308mRzqAwujSywQbYfwc0iU8zqjS0yQ6ztEHX9332KCaGNIYB/Qq1z3yN0oDZBWyeFYJBCkm2sXLhDtpKFwNDMu5TnrZpYGiHbK4Nlwikg5DrYV1g6iPoJmzE5MKd/fOp53EPUaQZaLqH3u+vo2ELWp3wSyWuYGoj9EEIJoV3L9AUS/ZLsJpLNBXmqOu0CW6P5A/dx9IL0FAji/FYKot9EqE0Tvs6QBUe/2CxMEkZAlBNGPhdoAQWyTSmbxUwvUygwQyMmniAPgLt87CODXHuftWJIQgzrfQDC5AfwSgz9MmmG/gWCOqDgZ4JsQeTvZBoJJDhAFEsSDyxUEEUUekk0UEMhjBcEcGsoWVpBU3NcCgkkPkJWrKbdRZvULCMTWhYEdMrayBQRyqHcnSLmAIH7LcWJ8Hch7BsHEdWFpJsZjziCgFBpZ9TPm4e0XBJTTJKt9xjy8RoLI4gimPLP5goCSgWTrEcyzsy8IqmZVMo0H5bJiQToBCOjZ5RcElhjLN3dU7uQMAvoxwQkJZKI1CQzCthJYEigahHuDDi4rFwzCPQ7F1fiDQZgTR5iJwEGYRgIsiECD8BwwMAEfDcIaW8CRBQdhjS1kJQEchDEFhiRKr4KDFPS9FGQNVwEHoW83QjsEHdkfnuIOl6C1NjMItiaCaCWgbdpFJXQ9soh2uoB9aJcCxFdgZwlcrTmvENGlrITBBdpK25Qhd1F2RScq8CKu/gsCL8qN5THjy+Rr5E6joYgPxpdl518QrCf8Kpgjn6C8HLkbb+vt7ZM8wdVvy258khsRfHaS5DalDnlidZT7Erk+SXV5Bj1D3LS29XyhVJuoKHs9Q8S6reK11oUc7vPcr9uswP3SLiDINefXOF5rwCuGzVT6zVkVPfh2wWmHcz4wAwba2cgN1/Tsvleu7//i69CgVyt1GwjOs2+XK3rtbl151Tg3vOeioG40Mz2V+6pQ4xbJHOZj6g0EMxk93tV7fuedvVZpQSPhbwNBGInrymGrwNh1GXmL8F+lAaJ+NU/fzcmvJqvKj7177+1v1GY/GiBKI1Fdy/2XK6upXwaIJpI8B/399W0mH9zzafKaeCF9J0WF+jyCuFusTGzZKhFH8dVLZql2brxgcdVBKb7KG/7UZTmB3XJ6uL/QYT5ScRI74FcHEJ7feopyfGkaeaGlPoCw/BbjZmSBWIvINQNmTxdjWJqwUI8sztR4nYPuIPSTSUnOCZOE3ierqRoJfNSQxDjLEYs8i91eqgFCDSWiFHiuqAN9CwEGCPEISVjvwhS7Mfx6dtX8kC5aqvneGBOEFN2v6RBiYwr3DQOkLhEW6fHFbIwFQnkLiWYmZxE220z/aedPx99C+hiyKR4OzNFhg8S75CJTnxQ1dyugHTLaY10iu9dBpmhQtMz1ABLrkgtHVnRsPUO3OcU25i8cWdGxZbflCBKJqBdMs3aF/dYhNexU9RFcYEmLXYQKghyWdufyldBSU3KpjkKhZclxTXQGCTkL/HZDUIH5+Gkt4SgoCtj7pSYSNJLTK3VVRnmXZxebSMBIzmHABeIdXBebiN9eHYtUZ62ab3BdGkUm+SKJw1bdRXeewaX7qqdAnljg2sVxg3guAk3baofcg9yZ2eZpnHNvSFrEqhB9YPjesmt0pt6Xc8hl7W5L9Q4Xx09ctsrd5VhWeF6nF8SRrZdw49qns//0xTK/AZ8vGr3caTliuzeFNeCJTgafpKlhHd2WP1sy1LqDF798gjKJPLqDr9keoTd43+NyNzC1CI8Xy2lcPtOaVBI5IiAWyQ3e125AcKoXs2Djhy5eVc3KiBxREIPkhjBiLhIjU++4T91IbggjRiCJLSEIwWGddkEaxlVN5KCArPHk8mXVpHk8FHH7JL3n5dPA7C90q7XkeFJucacNmGXeRfswLE71HA79efaGiCN/Ofjmfmtcp8X10tIsqCacV5xfRWjNUiXGYbovWgyFYHcQLak15K9oM5zqmgaeKsHJetbSHfSPzXOiw/rxE9YH4CXaUpsZ0ztemFurP95Jpyvrd29YTpIZr7cEJHqfc7Wl0PFm2+yJR70udaokKFtGPTdm8WdQe24+HmVLlueboWQquBcYYVH2vEzfh8kCks1p90eWsLCyZ8qK7E86Oe+3XYFnBuiWdth20UqZR5SvMoyPg3WNauJipi0LMTQgVq5xUUlZcrPsopPHJ926z8pm7xyFLrH/PxpHSoXKdWgXsLn1scZn1ZDd/2vszN3lt254qkE+qu3yoqLM+ghN3Qz2qcVzUC/ZMFsK/alU6l0OWV/bQz6v6yYbyuN5BaZ4A7Y30vs/PPksS2+qzlvfF7OQmzzcL7W+xa7OIfRuVdtn/tdvdFLnL4OTKcm2W16PmWc4FWWXNSlWM2n3D+uPxuyrcfo74aP+Ac30a82+oLmfAAAAAElFTkSuQmCC"
				style="width: 82px; height: 86px;">
				<figcaption>My Profile</figcaption></a>
		</div>
		
		<!-- customer Profile -->
		<div class="column">
			<a href="manageUsers.jsp"><img
				src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTG4SAYxcbhNYbz-xYtdG0LvErCiDIsn4f-1Y6GtP51DOrma9SY"
				style="width: 82px; height: 86px;">
				<figcaption>Manage Users</figcaption></a>
		</div>
		
		<!-- 	service list -->
		<div class="column">
			<a href="adminServiceList.jsp"><img
				src=https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQdU3g2jYiqEF6AeHpEh5c8MPu0YoqQjx7iHbA5NHMMMjaKF-Sy
				style="width: 82px; height: 86px;">
				<figcaption>Orders</figcaption></a>
		</div>
		
		<!-- Item price -->
		<div class="column">
			<a href="managePrice.jsp"><img
				src="data:image/jpeg;base64,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"
				style="width: 82px; height: 86px;">
				<figcaption>Price List</figcaption></a>
		</div>
	</div>





</body>
</html>
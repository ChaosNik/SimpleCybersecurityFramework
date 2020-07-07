<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="LoginServlet" method="post">
		<h2>Login</h2>

		<table style="width: 600px;">
			<tr>
				<td>Username:</td>
				<td><input type="text" name="username" style="width: 100%;"></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type="password" name="password" style="width: 100%;"></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="Login"></td>
			</tr>
		</table>

		<h3 style="color: red">
			<%
				if (null != request.getAttribute("errorMessage")) {
					out.println(request.getAttribute("errorMessage"));
				}
			%>
		</h3>
	</form>
</body>
</html>
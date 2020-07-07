<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="CommentServlet" method="post">
		<table style="width: 600px;">
			<tr>
				<td>Comment:</td>
				<td><textarea rows="10" cols="60" name="comment"></textarea></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="Add comment"></td>
			</tr>
		</table>
	</form>
</body>
</html>
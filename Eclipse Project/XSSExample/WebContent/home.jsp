<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<%
//allow access only if session exists
String user = null;
String lastComment = null;
if(session.getAttribute("user") == null){
	response.sendRedirect("login.jsp");
}else 
	user = (String) session.getAttribute("user");
lastComment = (String) session.getAttribute("lastComment");
%>

	<h3 style="color: black">
		Hello
		<%
		if (null != user) {
			out.println(user);
		}
	%>
	</h3>
	<br/>
	<a href="new_comment.jsp">New Comment</a>
	
	
	<h3 style="color: black">
		Last comment: 
		<%
		if (null != lastComment) {
			out.println(lastComment);
		}
	%>
	</h3>

	<form action="LogoutServlet" method="post">
		<input type="submit" value="Logout" >
	</form>

</body>
</html>
<%@ page import="java.lang.*"%>
<%
	//Retrieve variables
	String firstName = request.getParameter("firstName");
	String lastName = request.getParameter("lastName");
	
	//Create a session variable
	if (session.getAttribute("userName")==null ){
		session.setAttribute("userName", firstName+lastName);
	}%>
<html>
	<head>
		<title>Your first web form!</title>
	</head>
	<body>
		First:<%=firstName%> <br>
		Last: <%=lastName%><br>
		sessionName=<%=session.getAttribute("userName")%>
		
		
	</body>
</html>

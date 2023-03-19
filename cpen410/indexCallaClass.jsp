<%@ page import="java.lang.*"%>
<%@ page import="ut.CPEN410.*"%>

<html>
<head>
<title>My first JSP calling a class</title>
</head>
<body>


<%
	CPEN410Class exampleClass=new CPEN410Class(30);
	for (int i=1; i<=exampleClass.getValue();i++){
	%>
	

<%=exampleClass.getValue()%> <br>
	<%}%>


</body>
</html>
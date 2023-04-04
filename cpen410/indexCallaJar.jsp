<%@ page import="java.lang.*"%>
<%@ page import="ut.JAR.CPEN410.*"%>

<html>
<head>
<title>My first JSP calling a JAR</title>
</head>
<body>
<%
	CPEN410Class exampleClass=new CPEN410Class(10);
%>
<%=exampleClass.getValue()%>


</body>
</html>
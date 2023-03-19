<%@ page import="java.lang.*"%>

<%
	session.setAttribute("userName",null);
	session.setAttribute("currentPage",null);
	response.sendRedirect("loginHashing.html");

%>
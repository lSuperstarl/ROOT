<%@ page import="java.lang.*"%>
<%@ page import="ut.JAR.CPEN410.*"%>
<%//Import the java.sql package to use the ResultSet class %>
<%@ page import="java.sql.*"%>
<html>
<title>Mini-Facebook</title>
<%
        String username = (String)session.getAttribute("username");
%> 
<p>
    Hello <%= username %>!
</p>

<head>
</head>
<body>
    <div align="center"><h1>Welcome to Mini-Facebook</h1></div>
    <%
        applicationDBAuthenticationGoodComplete myAPPdb = new applicationDBAuthenticationGoodComplete();
        ResultSet pagesList = myAPPdb.listPagesAllowedForUser(username);
        while (pagesList.next()) {
            String page1 = pagesList.getString(2);
            %> <div align="left"><a href="<%= page1 %>"><%= page1 %></a></div>
            <%
        }
    %>
</body>
</html>
<%@ page import="java.lang.*"%>
<%@ page import="ut.JAR.CPEN410.*"%>
<%//Import the java.sql package to use the ResultSet class %>
<%@ page import="java.sql.*"%>
<html>
<title>Mini-Facebook</title>
<%
    applicationDBAuthenticationGoodComplete myAPPdb = new applicationDBAuthenticationGoodComplete();
    String username = (String)session.getAttribute("username");
    ResultSet imageResultSet = myAPPdb.getProfilePicture(username);
    String image = "";
    while (imageResultSet.next()) {
        image = imageResultSet.getString(1);
    }
    System.out.println(image);
%> 
<p>
    Hello <%= username %>! <br>
    <img src="/cpen410/images/regularusers/<%= image %>" width="100" height="80">
</p>

<head>
</head>

<body>
    <div align="center"><h1>Welcome to Mini-Facebook</h1></div>
    <div align="left"><h1>Menu:</h1></div>

    <%
        ResultSet pagesList = myAPPdb.listPagesAllowedForUser(username);
        System.out.println("Listing Pages for User...");
        
        while (pagesList.next()) {
            String page1 = pagesList.getString(2);
            System.out.println("Page loaded: " + page1);
            %> <div id="pages" align="left"><a href="<%= page1 %>"><%= page1 %></a></div>
            <%
        } 
    %>
</body>
</html>
<%@ page import="java.lang.*"%>
<%@ page import="ut.JAR.CPEN410.*"%>
<%//Import the java.sql package to use the ResultSet class %>
<%@ page import="java.sql.*"%>
<html>
<title>Mini-Facebook</title>

<%
    	//Check the authentication process
	if ((session.getAttribute("userName")==null) || (session.getAttribute("currentPage")==null)){
		session.setAttribute("currentPage", null);
		session.setAttribute("userName", null);
		response.sendRedirect("login.html");
	}
	else{

        String currentPage= "homePage.jsp";
		String userName = (String)session.getAttribute("userName");
		String previousPage = session.getAttribute("currentPage").toString();
		System.out.println("Current page: " + currentPage + ", Username: " + userName + ", Previous Page: " + previousPage);
		
		//Try to connect the database using the applicationDBManager class
		try{
				//Create the appDBMnger object
				applicationDBAuthenticationGoodComplete appDBAuth = new applicationDBAuthenticationGoodComplete();
				System.out.println("Connecting...");
				System.out.println(appDBAuth.toString());
				
				//Call the listAllDepartment method. This method returns a ResultSet containing all the tuples in the table Department
				boolean res = appDBAuth.verifyUser(userName, currentPage, previousPage);

				//Verify if the user has been authenticated
				if (res) {
					
                    // Create the current page attribute
					session.setAttribute("currentPage", "homePage.jsp");

					//Create a session variable
					if (session.getAttribute("userName") == null ){
						//create the session variable
						session.setAttribute("userName", userName);
					} else {
						//Update the session variable
						session.setAttribute("userName", userName);
					}
					
				} else {
					//Close any session associated with the user
					session.setAttribute("userName", null);
					
					//return to the login page
					response.sendRedirect("login.html");
					}

					//Close the connection to the database
					appDBAuth.close();
				
				} catch(Exception e) {
					e.printStackTrace();
					response.sendRedirect("login.html");
				} finally {
					System.out.println("Finally");
				}
				
	}%>

<%
    applicationDBAuthenticationGoodComplete myAPPdb = new applicationDBAuthenticationGoodComplete();
    String username = (String)session.getAttribute("userName");
    ResultSet imageResultSet = myAPPdb.getProfilePicture(username);
    String image = "";
    while (imageResultSet.next()) {
        image = imageResultSet.getString(1);
    }
%> 
<p>
	<h2>Hello <%= username %>!</h2>
    <img src="/cpen410/images/regularusers/<%= image %>" width="100" height="80">
</p>

<head>
</head>

<body>
    <div align="center"><h1>Welcome to Mini-Facebook</h1></div>
    <div align="left"><h1>Menu:</h1></div>

    <%
        ResultSet pagesList = myAPPdb.listPagesAllowedForUser(username);
        while (pagesList.next()) {
            String page1 = pagesList.getString(1);
            %> <div align="left"> - <a href="<%= page1 %>"><%= page1 %></a></div>
            <%
        } 
    %>
</body>
</html>
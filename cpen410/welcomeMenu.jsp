<%@ page import="java.lang.*"%>
<%@ page import="ut.JAR.CPEN410.*"%>
<%//Import the java.sql package to use the ResultSet class %>
<%@ page import="java.sql.*"%>
<html>
	<head>
		<title>Your first web form!</title>
	</head>
	<body>

	<a href="signout.jsp">Sign out</a>
<%
	//Check the authentication process
	if ((session.getAttribute("userName")==null) || (session.getAttribute("currentPage")==null)){
		session.setAttribute("currentPage", null);
		session.setAttribute("userName", null);
	}
	else{
	
		String currentPage= "welcomeMenu.jsp";
		String userName = session.getAttribute("userName").toString();
		String previousPage = session.getAttribute("currentPage").toString();
		
		//Try to connect the database using the applicationDBManager class
		try{
				//Create the appDBMnger object
				applicationDBAuthenticationGoodComplete appDBAuth = new applicationDBAuthenticationGoodComplete();
				System.out.println("Connecting...");
				System.out.println(appDBAuth.toString());
				
				//Call the listAllDepartment method. This method returns a ResultSet containing all the tuples in the table Department
				ResultSet res=appDBAuth.verifyUser(userName, currentPage, previousPage);
			
				
				
				//Verify if the user has been authenticated
				if (res.next()){
					String userActualName=res.getString(3);
					
					//Create the current page attribute
					session.setAttribute("currentPage", "welcomeMenu.jsp");
					
					//Create a session variable
					if (session.getAttribute("userName")==null ){
						//create the session variable
						session.setAttribute("userName", userName);
					} else{
						//Update the session variable
						session.setAttribute("userName", userName);
					}
					
					%>
					Welcome! <%=userActualName%>
				}else{
					//Close any session associated with the user
					session.setAttribute("userName", null);
					
					//return to the login page
					response.sendRedirect("login.html");
					}
					res.close();
					//Close the connection to the database
					appDBAuth.close();
				
				} catch(Exception e)
				{%>
					Nothing to show!
					<%e.printStackTrace();
					response.sendRedirect("login.html");
				}finally{
					System.out.println("Finally");
				}
				
	}%>		
			
			
	</body>
</html>

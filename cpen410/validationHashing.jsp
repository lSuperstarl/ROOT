<%@ page import="java.lang.*"%>
<%@ page import="ut.JAR.CPEN410.*"%>
<%//Import the java.sql package to use the ResultSet class %>
<%@ page import="java.sql.*"%>
<html>
	<head>
		<title>Your first web form!</title>
	</head>
	<body>

<%
	//Retrieve variables
	String username = request.getParameter("username");
	String password = request.getParameter("password");
	
	
	//Try to connect the database using the applicationDBManager class
	try{
			//Create the appDBMnger object
			applicationDBAuthenticationGoodComplete appDBAuth = new applicationDBAuthenticationGoodComplete();
			System.out.println("Connecting...");
			System.out.println(appDBAuth.toString());
			
			//Call the listAllDepartment method. This method returns a ResultSet containing all the tuples in the table Department
			ResultSet res=appDBAuth.authenticate(username, password);%>
		
			
			
			<%//Verify if the user has been authenticated
			if (res.next()){
				
				
				//Create the current page attribute
				session.setAttribute("currentPage", "validationHashing.jsp");
				
				//Create a session variable
				if (session.getAttribute("username")==null ){
					//create the session variable
					session.setAttribute("username", username);
				} else{
					//Update the session variable
					session.setAttribute("username", username);
				}
				
				//redirect to the welcome page
				//response.sendRedirect("welcome.jsp");
				response.sendRedirect("homePage.html");
				
			}else{
				//Close any session associated with the user
				session.setAttribute("username", null);
				
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
			%>		
		sessionName=<%=session.getAttribute("username")%>
		
		
	</body>
</html>

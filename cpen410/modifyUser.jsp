<%@ page import="java.lang.*"%>
<%@ page import="ut.JAR.CPEN410.*"%>
<%//Import the java.sql package to use the ResultSet class %>
<%@ page import="java.sql.*"%>
<html>
	<head>
		<title>Mini-Facebook!</title>
	</head>
	<body>

<%
	//Retrieve variables
	String userName = request.getParameter("userName");
	String newCompleteName = request.getParameter("completeName");
	String newUserTelephone = request.getParameter("userTelephone");
	String newUserEmail = request.getParameter("userEmail");
	String newStreet = request.getParameter("street");
	String newTown = request.getParameter("town");
	String newState = request.getParameter("state");
	String newCountry = request.getParameter("country");
	String newDegree = request.getParameter("degree");
	String newSchool = request.getParameter("school");
	
	
	
	//Try to connect the database using the applicationDBManager class
	try{
			//Create the appDBMnger object
			applicationDBAuthenticationGoodComplete appDBAuth = new applicationDBAuthenticationGoodComplete();
			System.out.println("Connecting...");
			System.out.println(appDBAuth.toString());
			
			//Call the listAllDepartment method. This method returns a ResultSet containing all the tuples in the table Department
			boolean res=appDBAuth.modifyUser(userName, newCompleteName, newUserTelephone, newUserEmail, newStreet, newTown, newState, newCountry, newDegree, newSchool);%>
		
			
			
			<%//Verify if the user has been authenticated
			if (res){%>
				response.sendRedirect("homePage.jsp");
			<%}else{
				//Close any session associated with the user
				response.sendRedirect("homePage.jsp");
				%>
			<%}
				
				//Close the connection to the database
				appDBAuth.close();
			
			} catch(Exception e)
			{%>
				Nothing to show!
				<%e.printStackTrace();
			}finally{
				System.out.println("Finally");
			}
			%>		
		sessionName=<%=session.getAttribute("userName")%>
	</body>
</html>

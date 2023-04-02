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
	String userPass = request.getParameter("userPass");
	String completeName = request.getParameter("completeName");
	String telephone = request.getParameter("userTelephone");
	String dateOfBirth = request.getParameter("dateOfBirth");
	String gender = request.getParameter("gender");
	String userEmail = request.getParameter("userEmail");
	String street = request.getParameter("street");
	String town = request.getParameter("town");
	String state = request.getParameter("state");
	String country = request.getParameter("country");
	String degree = request.getParameter("degree");
	String school = request.getParameter("school");
	
	
	
	//Try to connect the database using the applicationDBManager class
	try{
			//Create the appDBMnger object
			applicationDBAuthenticationGoodComplete appDBAuth = new applicationDBAuthenticationGoodComplete();
			System.out.println("Connecting...");
			System.out.println(appDBAuth.toString());
			
			//Call the listAllDepartment method. This method returns a ResultSet containing all the tuples in the table Department
			boolean res=appDBAuth.addUser(userName, completeName, userPass, telephone, dateOfBirth, gender, userEmail, street, town, state, country, degree, school);%>
		
			
			
			<%//Verify if the user has been authenticated
			if (res){%>
				USer added
			<%}else{
				//Close any session associated with the user
				session.setAttribute("userName", null);
				%>
				Cannot be added <br>
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

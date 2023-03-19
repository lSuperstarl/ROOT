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
	String userName = request.getParameter("userName");
	String userPass = request.getParameter("userPass");
	
	
	//Try to connect the database using the applicationDBManager class
	try{
			//Create the appDBMnger object
			applicationDBAuthentication appDBAuth = new applicationDBAuthentication();
			System.out.println("Connecting...");
			System.out.println(appDBAuth.toString());
			
			//Call the listAllDepartment method. This method returns a ResultSet containing all the tuples in the table Department
			ResultSet res=appDBAuth.authenticate(userName, userPass);%>
		
			
			
			<%//Verify if the user has been authenticated
			if (res.next()){
				//Move the iterator to the front
				res.beforeFirst();%>
			<table>
			
				<%//Create a session variable
				if (session.getAttribute("userName")==null ){
					session.setAttribute("userName", userName);
				}
				//Iterate over the ResultSet
				while (res.next())
				{	%>
					<tr>
					<%//All roles%>
					<td><%=res.getString(1)%></td><td><%=res.getString(2)%></td>
					</tr>
					
				<%}
				//Close the ResultSet%>
				</table>
			<%}else{
				//Close any session associated with the user
				session.setAttribute("userName", null);
				%>
				The user cannot be authenticated <br>
			<%}
				res.close();
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

<%/******
 This page create a connection to the Department database, and list all departments in the table department using the 
//ut.JAR.CPEN410.ApplicationDBManager class.**/
%>

<%//Import the ut.JAR.CPEN410 package for accessing the database %>
<%@ page import="ut.JAR.CPEN410.*"%>
<%//Import the java.sql package to use the ResultSet class %>
<%@ page import="java.sql.*"%>


<html>
	<head>
		<title>Your first web form!</title>
	</head>
	<body>
	<table border=1>
	<tr><td>Name</td><td>Building</td></tr>
	<%
	//Try to connect the database using the applicationDBManager class
	try{
			//Create the appDBMnger object
			applicationDBManager appDBMnger = new applicationDBManager();
			System.out.println("Connecting...");
			System.out.println(appDBMnger.toString());
			
			//Call the listAllDepartment method. This method returns a ResultSet containing all the tuples in the table Department
			ResultSet res=appDBMnger.listAllDepartment();
		
		
			int count=0;
			//Iterate over the ResultSet
			while (res.next())
			{
				//Count each retrieved record from the query
				count++;%>
				<tr>
				<%//Print the DepartmentName and the Building%>
				<td><%=res.getString(1)%></td><td><%=res.getString(2)%></td>
				</tr>
				
			<%}
				//Print the number of retrieved records
			%>
		</table>
			Count:  <%=count%>
			
			<%
			//Close the ResultSet
			res.close();
			//Close the connection to the database
			appDBMnger.close();
			
		} catch(Exception e)
		{%>
			Nothing to show!
			<%e.printStackTrace();
		}%>		
		
	</body>
</html>
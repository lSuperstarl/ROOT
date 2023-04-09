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
			// Set the current page and previous page
			String currentPage = "addUser.jsp";
			try {
				// Get the user input parameters
				String userToAdd = request.getParameter("userName");
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
		
				// Create a new instance of an authentication class
				applicationDBAuthenticationGoodComplete appDBAuth = new applicationDBAuthenticationGoodComplete();
		
					// Get the user's actual name
					// Set the current page attribute in the session to "addUser.jsp"
					session.setAttribute("currentPage", "addUser.jsp");

					// Set username to user that just signed up
					session.setAttribute("userName", userToAdd);
					
					// Add the user to the database
					appDBAuth.addUser(userToAdd, completeName, userPass, telephone, dateOfBirth, gender, userEmail, street, town, state, country, degree, school);
					// Redirect the user to the home page
					response.sendRedirect("uploadProfilePicture.html");
			} 
			// If there is an exception
			catch(Exception e) {
				// Print the stack trace of the exception
				e.printStackTrace();
				// Redirect the user to the home page
				response.sendRedirect("login.html");
			} 
			// Finally, print "Finally" to the console
			finally {
				System.out.println("Finally");
			}
		%>
	</body>
</html>

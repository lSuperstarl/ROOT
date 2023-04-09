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
    	//Check the authentication process
		if ((session.getAttribute("userName")==null) || (session.getAttribute("currentPage")==null)){
			session.setAttribute("currentPage", null);
			session.setAttribute("userName", null);
			response.sendRedirect("login.html");
		}
		else if (session.getAttribute("userName") != "admin") {
			session.setAttribute("currentPage", null);
			session.setAttribute("userName", null);
			response.sendRedirect("login.html");
		}
	else{
		//Retrieve variables
		String userToChange = request.getParameter("userName");
		String newCompleteName = request.getParameter("completeName");
		String newUserTelephone = request.getParameter("userTelephone");
		String newUserEmail = request.getParameter("userEmail");
		String newStreet = request.getParameter("street");
		String newTown = request.getParameter("town");
		String newState = request.getParameter("state");
		String newCountry = request.getParameter("country");
		String newDegree = request.getParameter("degree");
		String newSchool = request.getParameter("school");
        String currentPage= "homePage.jsp";
		String userName = (String)session.getAttribute("userName");
		String previousPage = session.getAttribute("currentPage").toString();
		
		//Try to connect the database using the applicationDBManager class
		try{
				//Create the appDBMnger object
				applicationDBAuthenticationGoodComplete appDBAuth = new applicationDBAuthenticationGoodComplete();
				System.out.println("Connecting...");
				System.out.println(appDBAuth.toString());
				
				//Call the listAllDepartment method. This method returns a ResultSet containing all the tuples in the table Department
				ResultSet res = appDBAuth.verifyUser(userName, currentPage, previousPage);

                System.out.println("Printing Result Set: ");
                System.out.println(res);

				//Verify if the user has been authenticated
				if (res.next()) {
					String userActualName=res.getString(2);
                    //Create the appDBMnger object

					System.out.println("Connecting...");
					System.out.println(appDBAuth.toString());
					
					//Call the listAllDepartment method. This method returns a ResultSet containing all the tuples in the table Department
					appDBAuth.modifyUser(userToChange, newCompleteName, newUserTelephone, newUserEmail, newStreet, newTown, newState, newCountry, newDegree, newSchool);
                    
					// Create the current page attribute
					session.setAttribute("currentPage", "modifyUser.jsp");

					//Create a session variable
					if (session.getAttribute("userName")==null ){
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
					res.close();
					//Close the connection to the database
					appDBAuth.close();
				
				} catch(Exception e) {
					e.printStackTrace();
					response.sendRedirect("login.html");
				} finally {
					System.out.println("Finally");
				}
				
	}%>
		<button onclick="window.location.href = 'homePage.jsp';">Go to Home Page</button>
	</body>
</html>

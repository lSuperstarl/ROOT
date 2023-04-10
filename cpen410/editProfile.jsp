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
// Check if user is authenticated
if ((session.getAttribute("userName") == null) || (session.getAttribute("currentPage") == null)) {
    // If user is not authenticated, redirect to login page and reset session attributes
    session.setAttribute("currentPage", null);
    session.setAttribute("userName", null);
    response.sendRedirect("login.html");
} else {
    String currentPage = "editProfile.jsp";
    String userName = (String) session.getAttribute("userName");
    String previousPage = session.getAttribute("currentPage").toString();

    // Try to connect the database using the applicationDBManager class
    try {
        // Create the appDBMnger object
        applicationDBAuthenticationGoodComplete appDBAuth = new applicationDBAuthenticationGoodComplete();
        System.out.println("Connecting...");
        System.out.println(appDBAuth.toString());

        // Call the listAllDepartment method. This method returns a ResultSet containing all the tuples in the table Department
        boolean res = appDBAuth.verifyUser(userName, currentPage, previousPage);


        // Verify if the user has been authenticated
        if (res) {
            // Retrieve variables
            String userToChange = session.getAttribute("userName").toString();
            String newCompleteName = request.getParameter("completeName");
            String newUserTelephone = request.getParameter("userTelephone");
            String newUserEmail = request.getParameter("userEmail");
            String newStreet = request.getParameter("street");
            String newTown = request.getParameter("town");
            String newState = request.getParameter("state");
            String newCountry = request.getParameter("country");
            String newDegree = request.getParameter("degree");
            String newSchool = request.getParameter("school");
            System.out.println(userName);

            appDBAuth.modifyUser(userToChange, newCompleteName, newUserTelephone, newUserEmail, newStreet, newTown, newState, newCountry, newDegree, newSchool);


            // Create the current page attribute
            session.setAttribute("currentPage", "editProfile.jsp");

            // Create a session variable
            if (session.getAttribute("userName") == null) {
                // Create the session variable
                session.setAttribute("userName", userName);
            } else {
                // Update the session variable
                session.setAttribute("userName", userName);
            }

            response.sendRedirect("homePage.jsp");
        } else {
            // Close any session associated with the user
            session.setAttribute("userName", null);

            // Return to the login page
            response.sendRedirect("login.html");
        }
        // Close the connection to the database
        appDBAuth.close();
    } catch (Exception e) {
        e.printStackTrace();
        response.sendRedirect("login.html");
    } finally {
        System.out.println("Finally");
    }
}
%>
		<button onclick="window.location.href = 'homePage.jsp';">Go to Home Page</button>
	</body>
</html>

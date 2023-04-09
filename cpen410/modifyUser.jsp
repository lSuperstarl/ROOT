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
    	// Check the authentication process
if ((session.getAttribute("userName")==null) || (session.getAttribute("currentPage")==null)){
    // If user not authenticated, redirect to login page
    session.setAttribute("currentPage", null);
    session.setAttribute("userName", null);
    response.sendRedirect("login.html");
}
else if (!session.getAttribute("userName").equals("admin")) {
    // If user is not an admin, redirect to login page
    System.out.println("In else if username != admin");
    session.setAttribute("currentPage", null);
    session.setAttribute("userName", null);
    response.sendRedirect("login.html");
}
else{
    // Retrieve variables from the request parameters and session attributes
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
    
    // Try to connect the database using the applicationDBManager class
    try{
        // Create the appDBMnger object
        applicationDBAuthenticationGoodComplete appDBAuth = new applicationDBAuthenticationGoodComplete();
        System.out.println("Connecting...");
        System.out.println(appDBAuth.toString());
        
        // Call the verifyUser method. This method verifies if the user has been authenticated
        ResultSet res = appDBAuth.verifyUser(userName, currentPage, previousPage);
        System.out.println("Printing Result Set: ");
        System.out.println(res);

        // If user has been authenticated
        if (res.next()) {
            String userActualName=res.getString(2);
            // Modify user information in the database using the modifyUser method
            appDBAuth.modifyUser(userToChange, newCompleteName, newUserTelephone, newUserEmail, newStreet, newTown, newState, newCountry, newDegree, newSchool);

            // Create the current page attribute
            session.setAttribute("currentPage", "modifyUser.jsp");

            // Create or update the session variable
            if (session.getAttribute("userName")==null ){
                session.setAttribute("userName", userName);
            } else {
                session.setAttribute("userName", userName);
            }
        } else {
            // Close any session associated with the user
            session.setAttribute("userName", null);
            // Redirect to login page
            response.sendRedirect("login.html");
        }
        // Close result set
        res.close();
        // Close the connection to the database
        appDBAuth.close();
    } catch(Exception e) {
        e.printStackTrace();
        // Redirect to login page
        response.sendRedirect("login.html");
    } finally {
        System.out.println("Finally");
    }
}
%>
		<button onclick="window.location.href = 'homePage.jsp';">Go to Home Page</button>
	</body>
</html>

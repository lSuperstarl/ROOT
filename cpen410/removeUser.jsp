<%@ page import="java.sql.*" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.Period" %>
<%@ page import="ut.JAR.CPEN410.*" %>
<html>
<head>
	<title>User Search Results</title>
</head>
<body>
    <style>
        table {
            border-collapse: collapse;
            width: 100%;
        }
        
        th, td {
            padding: 8px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        
        tr:hover {
            background-color: #f5f5f5;
        }
        
        th {
            background-color: #4CAF50;
            color: white;
        }
    </style>
    <%
// Check the authentication process
if ((session.getAttribute("userName") == null) || (session.getAttribute("currentPage") == null)) {
    session.setAttribute("currentPage", null);
    session.setAttribute("userName", null);
    response.sendRedirect("login.html");
} else if (!session.getAttribute("userName").equals("admin")) {
    session.setAttribute("currentPage", null);
    session.setAttribute("userName", null);
    response.sendRedirect("login.html");
} else {
    String currentPage = "removeUser.jsp";
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

        System.out.println("Printing Result Set: ");
        System.out.println(res);

        // Verify if the user has been authenticated
        if (res) {

            // Create the current page attribute
            session.setAttribute("currentPage", "removeUser.jsp");

            String username = request.getParameter("username");
            appDBAuth.removeUser(username);
            response.sendRedirect("homePage.jsp");

            // Create a session variable
            if (session.getAttribute("userName") == null) {
                // Create the session variable
                session.setAttribute("userName", userName);
            } else {
                // Update the session variable
                session.setAttribute("userName", userName);
            }
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

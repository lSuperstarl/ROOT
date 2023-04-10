<%@ page import="java.sql.*" %>
<%@ page import="java.io.*" %>
<%@ page import="ut.JAR.CPEN410.*"%>
<%
// Check if the "userName" or "currentPage" attribute is null in the current session
if ((session.getAttribute("userName") == null) || (session.getAttribute("currentPage") == null)) {
    // If either of them is null, set the session attributes to null
    session.setAttribute("currentPage", null);
    session.setAttribute("userName", null);
    // Redirect the user to the login page
    response.sendRedirect("login.html");
} 
// Check if the "userName" attribute is not equal to "admin"
else if (!session.getAttribute("userName").equals("admin")) {
    // If it is not, set the session attributes to null
    session.setAttribute("currentPage", null);
    session.setAttribute("userName", null);
    // Redirect the user to the login page
    response.sendRedirect("login.html");
}
// If the "userName" attribute is equal to "admin"
else {
    String currentPage = "adminAddUser.jsp";
    String userName = (String) session.getAttribute("userName");
    String previousPage = session.getAttribute("currentPage").toString();
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

        // Verify the user with the current and previous page
        boolean res = appDBAuth.verifyUser(userName, currentPage, previousPage);

        // If the verification is successful
        if (res) {

            // Set the current page attribute in the session to "adminAddUser.jsp"
            session.setAttribute("currentPage", "adminAddUser.jsp");
            // Add the user to the database
            appDBAuth.addUser(userToAdd, completeName, userPass, telephone, dateOfBirth, gender, userEmail, street, town, state, country, degree, school);
            // Redirect the user to the home page
            response.sendRedirect("homePage.jsp");
        } 
        // If the verification fails
        else {
            appDBAuth.close();
            // Redirect the user to the home page
            response.sendRedirect("homePage.jsp");
        }
    } 
    // If there is an exception
    catch(Exception e) {
        // Print the stack trace of the exception
        e.printStackTrace();
        // Redirect the user to the home page
        response.sendRedirect("homePage.jsp");
    } 
    // Finally, print "Finally" to the console
    finally {
        System.out.println("Finally");
    }
}

%>

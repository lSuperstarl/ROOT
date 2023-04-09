<%@ page import="java.sql.*" %>
<%@ page import="java.io.*" %>
<%@ page import="ut.JAR.CPEN410.*"%>
<%
if ((session.getAttribute("userName")==null) || (session.getAttribute("currentPage")==null)){
    session.setAttribute("currentPage", null);
    session.setAttribute("userName", null);
    response.sendRedirect("login.html");
} else if (!session.getAttribute("userName").equals("admin")) {
    session.setAttribute("currentPage", null);
    session.setAttribute("userName", null);
    response.sendRedirect("login.html");
} else {
    String currentPage= "adminAddUser.jsp";
    String previousPage = session.getAttribute("currentPage").toString();
    try {
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

        applicationDBAuthenticationGoodComplete appDBAuth = new applicationDBAuthenticationGoodComplete();

        ResultSet res = appDBAuth.verifyUser((String)session.getAttribute("userName"), currentPage, previousPage);

        if (res.next()) {
            String userActualName=res.getString(2);
            session.setAttribute("currentPage", "adminAddUser.jsp");
            appDBAuth.addUser(userToAdd, completeName, userPass, telephone, dateOfBirth, gender, userEmail, street, town, state, country, degree, school);
            response.sendRedirect("homePage.jsp");

        } else {
            res.close();
            appDBAuth.close();
            response.sendRedirect("homePage.jsp");

        }
    } catch(Exception e) {
        e.printStackTrace();
        response.sendRedirect("homePage.jsp");

    } finally {
        System.out.println("Finally");
    }
}
%>

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
	else{

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
                    
                    // Create the current page attribute
					session.setAttribute("currentPage", "addUser.jsp");

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
				
			<%}else{
				%>
				User added <br>
				<%
				session.setAttribute("userName", userName);
				response.sendRedirect("uploadProfilePicture.html");
				%>
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

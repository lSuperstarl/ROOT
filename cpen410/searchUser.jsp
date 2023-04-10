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

<h1>Search Results</h1> 
<%
//Check the authentication process
if ((session.getAttribute("userName") == null) && (session.getAttribute("currentPage")==null)){
	session.setAttribute("currentPage", null);
	session.setAttribute("userName", null);
	response.sendRedirect("login.html");
}
else{

	String currentPage= "searchUser.jsp";
	String userName = (String)session.getAttribute("userName");
	String previousPage = session.getAttribute("currentPage").toString();
	//Try to connect the database using the applicationDBManager class
	try{
			//Create the appDBMnger object
			applicationDBAuthenticationGoodComplete appDBAuth = new applicationDBAuthenticationGoodComplete();
			System.out.println("Connecting...");
			System.out.println(appDBAuth.toString());
			
			//Call the listAllDepartment method. This method returns a ResultSet containing all the tuples in the table Department
			boolean res = appDBAuth.verifyUser(userName, currentPage, previousPage);

			//Verify if the user has been authenticated
			if (res) {

				String Name = request.getParameter("completeName");
				String age = request.getParameter("age");
				String gender = request.getParameter("gender");
				String street = request.getParameter("street");
				String town = request.getParameter("town");
				String state = request.getParameter("state");
				String country = request.getParameter("country");
				
				String parameters = "";
				if (!Name.isEmpty()) {
					parameters += Name + " ";
				}
				if (!age.isEmpty()) {
					parameters += age + " ";
				}
				if (!gender.isEmpty()) {
					parameters += gender + " ";
				}
				if (!street.isEmpty()) {
					parameters += street + " ";
				}
				if (!town.isEmpty()) {
					parameters += town + " ";
				}
				if (!state.isEmpty()) {
					parameters += state + " ";
				}
				if (!country.isEmpty()) {
					parameters += country + " ";
				}

				if (!parameters.isEmpty()) {
					parameters = parameters.trim().replaceAll("\\s+", " and ");
					out.print("Results for '" + parameters + "' in database");
				}

				%>
				<table border="1">
					<thead>
						<tr>
							<th>Name</th>
							<th>Age</th>
							<th>Gender</th>
							<th>Street</th>
							<th>Town</th>
							<th>State</th>
							<th>Country</th>
						</tr>
					</thead>
					<tbody>
						<%
							try {
								ResultSet newRes = appDBAuth.searchUser(town, street, state, country, age, gender, Name);
								while (newRes.next()) {
									LocalDate birthDate = LocalDate.parse(newRes.getString("dob"));
									LocalDate currentDate = LocalDate.now();
									int userAge = Period.between(birthDate, currentDate).getYears();
						%>
						<tr>
							<td><%= newRes.getString("Name") %></td>
							<td><%= userAge %></td>
							<td><%= newRes.getString("gender") %></td>
							<td><%= newRes.getString("street") %></td>
							<td><%= newRes.getString("town") %></td>
							<td><%= newRes.getString("state") %></td>
							<td><%= newRes.getString("country") %></td>
						</tr>
						<%
								}
								appDBAuth.close();
							} catch (Exception e) {
								e.printStackTrace();
							}
						%>
					</tbody>
				</table>
				<%


				
				// Create the current page attribute
				session.setAttribute("currentPage", "searchUser.jsp");

				//Create a session variable
				if (session.getAttribute("userName") == null ){
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

				//Close the connection to the database
				appDBAuth.close();
			
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				System.out.println("Finally");
			}
}%>
	<button onclick="window.location.href = 'homePage.jsp';">Go to Home Page</button>
</body>
</html>

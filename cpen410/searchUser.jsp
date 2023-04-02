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
	<h1>
		User Search Results 
		<%
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
				out.print("for '" + parameters + "' in database");
			}
		%>
	</h1>

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
					applicationDBAuthenticationGoodComplete appDBAuth = new applicationDBAuthenticationGoodComplete();
					ResultSet res = appDBAuth.searchUser(town, street, state, country, age, gender, Name);
					while (res.next()) {
                        LocalDate birthDate = LocalDate.parse(res.getString("dob"));
                        LocalDate currentDate = LocalDate.now();
                        int userAge = Period.between(birthDate, currentDate).getYears();
			%>
			<tr>
				<td><%= res.getString("Name") %></td>
				<td><%= userAge %></td>
				<td><%= res.getString("gender") %></td>
				<td><%= res.getString("street") %></td>
				<td><%= res.getString("town") %></td>
				<td><%= res.getString("state") %></td>
				<td><%= res.getString("country") %></td>
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
</body>
</html>

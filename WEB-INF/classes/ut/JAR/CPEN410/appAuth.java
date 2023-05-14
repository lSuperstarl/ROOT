//This class belongs to the ut.JAR.CPEN410 package
package ut.JAR.CPEN410;
/********
CPEN 410 Mobile, Web, and Internet programming

This servlet perform a dummy authentication process
if the user is authenticated, it sends a json objects containing: id, name, userName and email
Required values:
	userName: user
	passWord: pass



******************/
//Import the java.sql package for managing the ResulSet objects
import java.sql.* ;
// Import required java libraries
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

// Extend HttpServlet class
public class appAuth extends HttpServlet {

	public MySQLCompleteConnector myDBConn;

	public appAuth(){
		//Create the MySQLConnector object
		myDBConn = new MySQLCompleteConnector();
		
		//Open the connection to the database
		myDBConn.doConnection();
	}

	private String message;
	private String title;

	public void init() throws ServletException {
		// Do required initialization
		message = "Hello World";
		title = "my first servlet";
	}

	private String hashingSha256(String plainText)
	{
			String sha256hex = org.apache.commons.codec.digest.DigestUtils.sha256Hex(plainText); 
			return sha256hex;
	}
/***
	doGet method: it is executed when the GET method is used for the http request
**/

   public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
     
	 
      // Set response content type
      response.setContentType("text/html");

      // Actual logic goes here.
      PrintWriter out = response.getWriter();
      
	  // Send the response
	  out.println("This servlet does not support authentication via GET method!");
	 }
/*****

	doPost method: this method is executed when the POST method is used for the http request

**/
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     
		//Retreive the http request parameters
		String param = request.getParameter("param");

		System.out.println("Receive request with parameter: " + param);

		if (param.equals("auth")) {
			System.out.println("Param is auth");
			String username = request.getParameter("user");
			String password = request.getParameter("pass");

			String hashingVal = hashingSha256(username + password);
			
			System.out.println("Received user: " + username + " with password: " + hashingVal);

			//Return the ResultSet containing all roles assigned to the user
			Boolean queryReturn = myDBConn.doAuthentication(username, hashingVal);

			if (queryReturn == true) {
				System.out.println("User: " + username + " Has logged In!");

				// Set response content type
				response.setContentType("text/html");
				String msg = doAuthentication(username);

				// Actual logic goes here.
				PrintWriter out = response.getWriter();

				//Send the final response to the requester
				out.println(msg);
				System.out.println(msg);
			}
			else {
				PrintWriter out = response.getWriter();
				out.println("not");	  
			}
		}
		else if (param.equals("search")) {
			System.out.println("Param is search");

			String gender = request.getParameter("gender");
			String location = request.getParameter("location");
			String dob = request.getParameter("dob");
			String usersFound = doSearch(gender, location, dob);

			PrintWriter out = response.getWriter();
			out.println(usersFound);
			System.out.println(usersFound);
		}
	}

	public String doSearch(String gender, String location, String dob) {
		String fields, tables, whereClause;

		fields = "userinformation.*, addressinformation.*, picturesforuser.PicturePath";
		tables = "userinformation INNER JOIN addressinformation ON userinformation.UserName = addressinformation.UserName INNER JOIN picturesforuser ON userinformation.UserName = picturesforuser.UserName";
		whereClause = "userinformation.gender = '" + gender + "'" + " AND addressinformation.country = '" + location + "'" + " AND userinformation.dob = '" + dob + "';";

		String query = "SELECT " + fields + " FROM " + tables + " WHERE " + whereClause;
		System.out.println(query);

		try {
			ResultSet userInfo = myDBConn.doSelect(query);
			StringBuilder msg = new StringBuilder("{\n\"userlist\": [\n");
		
			while (userInfo.next()) {
				String name = userInfo.getString("Name");
				String town = userInfo.getString("town");
				String state = userInfo.getString("state");
				String country = userInfo.getString("country");
				String street = userInfo.getString("street");
				String email = userInfo.getString("email");
				String image = userInfo.getString("PicturePath");
				String username = userInfo.getString("userName");
		
				msg.append("{\n");
				msg.append("\t\"name\": \"" + name + "\",\n");
				msg.append("\t\"email\": \"" + email + "\",\n");
				msg.append("\t\"userName\": \"" + username + "\",\n");
				msg.append("\t\"dob\": \"" + dob + "\",\n");
				msg.append("\t\"gender\": \"" + gender + "\",\n");
				msg.append("\t\"profilePicture\": \"" + image + "\",\n");
				msg.append("\t\"street\": \"" + street + "\",\n");
				msg.append("\t\"town\": \"" + town + "\",\n");
				msg.append("\t\"state\": \"" + state + "\",\n");
				msg.append("\t\"country\": \"" + country + "\"\n");
				msg.append("},\n");
			}
		
			// remove the last comma and close the JSON array and object
			if (msg.length() > 15) { // 15 is the length of the initial string
				msg.setLength(msg.length() - 2); // remove last comma
			}
			msg.append("\n]\n}");
		
			return msg.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}

	/****
		This method perform a dummy authentication process
	***/
	public String doAuthentication(String username) {
		String msg;
		String fields, tables, whereClause;
	
		fields = "userinformation.*, addressinformation.*, picturesforuser.PicturePath";
		tables = "userinformation INNER JOIN addressinformation ON userinformation.UserName = addressinformation.UserName INNER JOIN picturesforuser ON userinformation.UserName = picturesforuser.UserName";
		whereClause = "userinformation.UserName = '" + username + "'" + " AND addressinformation.UserName = '" + username + "';";
	
		String query = "SELECT " + fields + " FROM " + tables + " WHERE " + whereClause;
		System.out.println(query);
	
		try {
			ResultSet userInfo = myDBConn.doSelect(query);
	
			if (userInfo.next()) {
				String name = userInfo.getString("Name");
				String town = userInfo.getString("town");
				String state = userInfo.getString("state");
				String country = userInfo.getString("country");
				String dob = userInfo.getString("dob");
				String gender = userInfo.getString("gender");
				String street = userInfo.getString("street");
				String email = userInfo.getString("email");
				String image = userInfo.getString("PicturePath");
	
				// You got authenticated
				// then, generate the JSON object
				msg = "{\n";
				msg += "\t\"name\": \"" + name + "\", \n";
				msg += "\t\"email\": \"" + email + "\", \n";
				msg += "\t\"userName\": \"" + username + "\", \n";
				msg += "\t\"dob\": \"" + dob + "\", \n";
				msg += "\t\"gender\": \"" + gender + "\", \n";
				msg += "\t\"profilePicture\": \"" + image + "\", \n";
				msg += "\t\"street\": \"" + street + "\", \n";
				msg += "\t\"town\": \"" + town + "\", \n";
				msg += "\t\"state\": \"" + state + "\", \n";
				msg += "\t\"country\": \"" + country + "\"\n";
				msg += "}";
	
				return msg;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Return the actual message
		return null;
	}
	
	


   public void destroy() {
      // do nothing.
   }
}

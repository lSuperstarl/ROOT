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

// Extend HttpServlet class
public class appAuth extends HttpServlet {
 
   private String message;
   private String title;

   public void init() throws ServletException {
      // Do required initialization
      message = "Hello World";
	  title = "my first servlet";
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
	public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
     
	 //Retreive the http request parameters
	 String userName = request.getParameter("user");
	 String passwd = request.getParameter("pass");
     String sha256hex = org.apache.commons.codec.digest.DigestUtils.sha256Hex(userName + passwd);
	 

     //Declare function variables
		String fields, tables, whereClause, hashingVal;
		
		//Define the table where the selection is performed
		tables="userinformation, rolesforuser";
		//Define the list fields list to retrieve assigned roles to the user
		fields ="userinformation.username , rolesforuser.Id, userinformation.Name";
		hashingVal = sha256hex;
		whereClause="userinformation.username = rolesforuser.UserName and userinformation.username='" + userName +"' and passwordhash='" + hashingVal + "'";
		
		
		System.out.println("User: " + userName + " Has logged In!");
		
		//Return the ResultSet containing all roles assigned to the user
		myDBConn.doSelect(fields, tables, whereClause);

        // Set response content type
        response.setContentType("text/html");

        // Actual logic goes here.
        PrintWriter out = response.getWriter();
        
        //Perform the actual authentication process
        String msg = doAuthentication(userName, passwd);
        
        //Send the final response to the requester
        out.println(msg);
        System.out.println(msg);
	}

	/****
		This method perform a dummy authentication process
	***/
   public String doAuthentication(String userName, String passwd)
   {
		String authenticationString = userName+passwd;
		String msg;
		
		
		 if (authenticationString.compareTo("userpass")==0){
		  //You got authenticated
		  //then, generate the JSON object
		  msg="{\"userName\"=\"user\", \n";
		  msg+="\"name\"=\"Dummy Name\", \n";
		  msg+="\"email\"=\"dummy@dummy.com\", \n";
		  msg+="\"id\"=\"12345\" \n}";
		  }
		else{
		msg="not";	  
		  }
		  
		//Return the actual message
		return msg;
   }


   public void destroy() {
      // do nothing.
   }
}

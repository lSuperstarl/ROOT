
//This class belongs to the ut.JAR.CPEN410 package
package ut.JAR.CPEN410;

//Import the java.sql package for managing the ResulSet objects
import java.sql.* ;

//Import hashing functions
import org.apache.commons.codec.*;

// File Operations to handle byte streams when user uploads profile picture
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

/******
	This class authenticate users using userName and passwords

*/
@MultipartConfig(
		fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
		maxFileSize = 1024 * 1024 * 10,      // 10 MB
		maxRequestSize = 1024 * 1024 * 100   // 100 MB
	)
public class applicationDBAuthenticationGoodComplete extends HttpServlet{

	//myDBConn is an MySQLConnector object for accessing to the database
	private MySQLCompleteConnector myDBConn;
	
	/********
		Default constructor
		It creates a new MySQLConnector object and open a connection to the database
		@parameters:
		
	*/
	public applicationDBAuthenticationGoodComplete(){
		//Create the MySQLConnector object
		myDBConn = new MySQLCompleteConnector();
		
		//Open the connection to the database
		myDBConn.doConnection();
	}
	
	
	/*******
		authenticate method
			Authentication method
			@parameters:
			@returns:
				A ResultSet containing the userName and all roles assigned to her.
	*/

	public boolean removeUser(String username)
	{
		MySQLCompleteConnectorPrivileged myDBConn2 = new MySQLCompleteConnectorPrivileged();
		
		//Open the connection to the database
		myDBConn2.doConnection();
		boolean res;
		String addressinformation, userinformation, rolesforuser, where;
		
		addressinformation="addressinformation";
		where="addressinformation.username='" + username + "'";
		res=myDBConn2.doDelete(addressinformation, where);

		rolesforuser="rolesforuser";
		where="rolesforuser.username='" + username + "'";
		res=myDBConn2.doDelete(rolesforuser, where);


		userinformation="userinformation";
		where="userinformation.username='" + username + "'";
		res=myDBConn2.doDelete(userinformation, where);

		System.out.println("Deletion result: " + res);
		return res;
	}

	public ResultSet listPagesAllowedForUser(String username) {
		String tables = "rolesforuser, roles, roleforwebpage, webpages";
		String fields = "webpages.page, webpages.page";
		String whereClause = "rolesforuser.ID=roles.roleID and roles.roleID=roleforwebpage.roleID and roleforwebpage.pageID=webpages.pageID";
		whereClause += " and userName='" + username + "' order by webpages.page";

		String query = "SELECT " + fields + " FROM " + tables + " WHERE " + whereClause;

		return myDBConn.doPageSelect(query);
	}

	public void uploadPicture(InputStream fileContent, String fileName) throws ServletException, IOException {
		// Receive file uploaded to the Servlet from the HTML5 form
		File file = new File("C:\\apache-tomcat-8.5.85\\webapps\\ROOT\\cpen410\\images\\regularusers" + fileName);
		try (FileOutputStream out = new FileOutputStream(file)) {
			// Write the uploaded file to the file system
			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = fileContent.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
			}
		} catch (IOException e) {
			throw new ServletException("Error saving uploaded file", e);
		}
	}
	

	public ResultSet authenticate(String username, String password)
	{
		
		//Declare function variables
		String fields, tables, whereClause, hashingVal;
		
		//Define the table where the selection is performed
		tables="userinformation, rolesforuser";
		//Define the list fields list to retrieve assigned roles to the user
		fields ="userinformation.username , rolesforuser.Id, userinformation.Name";
		hashingVal = hashingSha256(username + password);
		whereClause="where userinformation.username = rolesforuser.UserName and userinformation.username='" + username +"' and passwordhash='" + hashingVal + "'";
		
		
		System.out.println("User: " + username + " Has logged In!");
		
		//Return the ResultSet containing all roles assigned to the user
		return myDBConn.doSelect(fields, tables, whereClause);
	}


	/*******
		menuElements method
			Authentication method
			@parameters:
			@returns:
				A ResultSet containing the userName and all roles assigned to her.
	*/
	public ResultSet menuElements(String userName)
	{
		
		//Declare function variables
		String fields, tables, whereClause, orderBy;
		
		//Define the table where the selection is performed
		tables="rolesforuser, roles, rolesforwebpage, webpages";
		//Define the list fields list to retrieve assigned roles to the user
		fields ="rolewebpagegood.pageURL, menuElement.title, webpagegood.pageTitle";
		whereClause=" rolesforuser.ID=role.roleID and role.roleID=rolewebpagegood.roleId and menuElement.menuID = webpagegood.menuID";
		whereClause+=" and rolewebpagegood.pageURL=webpagegood.pageURL";
		whereClause+=" and userName='"+ userName+"' order by menuElement.title, webpagegood.pageTitle;";
		
		
		System.out.println("listing...");
		
		//Return the ResultSet containing all roles assigned to the user
		return myDBConn.doSelect(fields, tables, whereClause);
	}	
	
	public ResultSet verifyUser(String userName, String currentPage, String previousPage)
	{
		
		//Declare function variables
		String fields, tables, whereClause, hashingVal;
		
		//Define the table where the selection is performed
		tables="roleusergood, role, rolewebpagegood, webpagegood, usergood, webpageprevious";
		//Define the list fields list to retrieve assigned roles to the user
		fields ="usergood.userName, roleusergood.roleId, usergood.Name ";
		whereClause=" usergood.userName = roleusergood.userName and usergood.userName='" +userName +"' and role.roleId=roleusergood.roleId and ";
		whereClause+=" rolewebpagegood.roleId=role.roleId and rolewebpagegood.pageURL=webpagegood.pageURL and webpagegood.pageURL='" +currentPage+"' and ";
		whereClause+=" webpageprevious.previousPageURL='"+previousPage+"'webpagegood.pageURL = webpageprevious.currentpageURL'";
		whereClause+=" webpage";
		
		
		System.out.println("listing...");
		
		//Return the ResultSet containing all roles assigned to the user
		return myDBConn.doSelect(fields, tables, whereClause);
		
		
	}
	
	public boolean addUser(String username, String completeName, String userpass, String userTelephone, String dateOfBirth, String gender, String userEmail, String street, String town, String state, String country, String degree, String school)
	{
		boolean res = false;
		String userTable = "UserInformation";
		String addressTable = "AddressInformation";
		String hashingValue = hashingSha256(username + userpass);

		String userValues = "'" + username + "', '" + hashingValue + "', '" + completeName + "', '" + userTelephone + "', '" + dateOfBirth + "', '" + gender + "', '" + userEmail + "'";
		res = myDBConn.doInsert(userTable, userValues);
		String addressValues = "'" + username + "', '" + degree + "', '" + school + "', '" + street + "', '" + town + "', '" + state + "', '" + country + "'";
		res &= myDBConn.doInsert(addressTable, addressValues);

		// Special insert to add automatic roles. Add userRole and change manually if needed.
		String query = "INSERT INTO RolesForUser (username, roleID) VALUES ('" + username + "', 2);";
		res &= myDBConn.doRoleInsert(query);
		
		System.out.println("Insertion result: " + res);
		return res;
	}
	
	/*********
		hashingSha256 method
			Generates a hash value using the sha256 algorithm.
			@parameters: Plain text
			@returns: the hash string based on the plainText
	*/
	private String hashingSha256(String plainText)
	{
			String sha256hex = org.apache.commons.codec.digest.DigestUtils.sha256Hex(plainText); 
			return sha256hex;
	}
	
	public ResultSet searchUser(String town, String street, String state, String country, String age, String gender, String name) {
		String fields, tables, whereClause;
	
		fields = "userinformation.*, addressinformation.*";
		tables = "userinformation INNER JOIN addressinformation ON userinformation.UserName = addressinformation.UserName";
		whereClause = "";
	
		if (town == null && street == null && state == null && country == null && age == null && gender == null && name == null) {
			// no search parameters provided, return empty ResultSet
			return myDBConn.doSelect(fields, tables, "");
		}        
	
		if (town != null && !town.isEmpty()) {
			whereClause += "addressinformation.Town = '" + town + "' AND ";
		}
		if (street != null && !street.isEmpty()) {
			whereClause += "addressinformation.Street = '" + street + "' AND ";
		}
		if (state != null && !state.isEmpty()) {
			whereClause += "addressinformation.State = '" + state + "' AND ";
		}
		if (country != null && !country.isEmpty()) {
			whereClause += "addressinformation.Country = '" + country + "' AND ";
		}
		if (age != null && !age.isEmpty()) {
			whereClause += "userinformation.dob <= DATE_SUB(CURDATE(), INTERVAL " + age + " YEAR) AND ";
		}
		if (gender != null && !gender.isEmpty()) {
			whereClause += "userinformation.gender = '" + gender + "' AND ";
		}
		if (name != null && !name.isEmpty()) {
			whereClause += "userinformation.Name = '" + name + "' AND ";
		}
	
		if (!whereClause.isEmpty()) {
			// remove trailing "AND " from whereClause
			whereClause = whereClause.substring(0, whereClause.length() - 5);
			whereClause = "WHERE " + whereClause;
		}
	
		System.out.println("listing search results...");
		return myDBConn.doSelect(fields, tables, whereClause);
	}
	
	
	
	
	/*********
		close method
			Close the connection to the database.
			This method must be called at the end of each page/object that instatiates a applicationDBManager object
			@parameters:
			@returns:
	*/
	public void close()
	{
		//Close the connection
		myDBConn.closeConnection();
	}

}
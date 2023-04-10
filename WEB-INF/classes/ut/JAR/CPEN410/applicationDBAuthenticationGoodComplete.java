
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
	
	
	/**
	 * 
	 * @param username
	 * @return Boolean to know if a user has been deleted or not
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
	/**
	 * 
	 * @param username
	 * @return ResultSet containing the name of each page
	 */
	public ResultSet listPagesAllowedForUser(String username) {
		MySQLCompleteConnectorPrivileged myDBConn2 = new MySQLCompleteConnectorPrivileged();
		// Open the connection to the database
		myDBConn2.doConnection();

		String query = "SELECT wp.Page, wp.Description FROM webPages wp INNER JOIN roleForWebPage rfw ON rfw.Page = wp.Page INNER JOIN RolesForUser rfu ON rfu.roleID = rfw.RoleID INNER JOIN UserInformation ui ON ui.UserName = rfu.UserName WHERE ui.UserName = '" + username + "';";

		return myDBConn.doPageSelect(query);
	}
	
	/**
	 * 
	 * @param file
	 * @param username
	 */
	public void setProfilePicture(String file, String username) {
		String sql = "INSERT INTO picturesForUser (PicturePath, UserName) VALUES (" + file + ", '" + username + "');";
		myDBConn.doInsertPicture(sql);
	}

	/**
	 * 
	 * @param file
	 * @param username
	 */
	public void updateProfilePicture(String file, String username) {
		String sql = "UPDATE picturesForUser SET PicturePath = '" + file + "' WHERE UserName = '" + username + "';";
		myDBConn.doInsertPicture(sql);
	}
	/**
	 * 
	 * @param username
	 * @return ResultSet containing the file name of the picture
	 */
	public ResultSet getProfilePicture(String username) {
		String query = "SELECT PicturePath from picturesForUser WHERE username = '" + username + "';";

		return myDBConn.doGetProfilePicture(query);
	}

	/**

	Modifies a user's information in the database.
	@param username the username of the user whose information will be modified
	@param newCompleteName the user's new complete name (optional)
	@param newUserTelephone the user's new telephone number (optional)
	@param newUserEmail the user's new email address (optional)
	@param newStreet the user's new street address (optional)
	@param newTown the user's new town or city (optional)
	@param newState the user's new state or province (optional)
	@param newCountry the user's new country (optional)
	@param newDegree the user's new degree (optional)
	@param newSchool the user's new school (optional)

	@return true if the modification was successful, false otherwise
	*/
	public boolean modifyUser(String username, String newCompleteName, String newUserTelephone, String newUserEmail, String newStreet, String newTown, String newState, String newCountry, String newDegree, String newSchool) {
		String setClause = " SET ";
		System.out.println(username);
		MySQLCompleteConnectorPrivileged myDBConn2 = new MySQLCompleteConnectorPrivileged();
		// Open the connection to the database
		myDBConn2.doConnection();
	
		boolean res;

		if (newCompleteName != null && !newCompleteName.isEmpty()) {
			setClause += "Name = '" + newCompleteName + "', ";
		}
		if (newUserTelephone != null && !newUserTelephone.isEmpty()) {
			setClause += "Telephone = '" + newUserTelephone + "', ";
		}
		if (newUserEmail != null && !newUserEmail.isEmpty()) {
			setClause += "Email = '" + newUserEmail + "', ";
		}
		if (newDegree != null && !newDegree.isEmpty()) {
			setClause += "Degree = '" + newDegree + "', ";
		}
		if (newSchool != null && !newSchool.isEmpty()) {
			setClause += "School = '" + newSchool + "', ";
		}
		if (newStreet != null && !newStreet.isEmpty()) {
			setClause += "a.Street = '" + newStreet + "', ";
		}
		if (newTown != null && !newTown.isEmpty()) {
			setClause += "a.Town = '" + newTown + "', ";
		}
		if (newState != null && !newState.isEmpty()) {
			setClause += "a.State = '" + newState + "', ";
		}
		if (newCountry != null && !newCountry.isEmpty()) {
			setClause += "a.Country = '" + newCountry + "', ";
		}

		// Remove trailing ", "
		if (!setClause.equals("SET ")) {
			setClause = setClause.substring(0, setClause.length() - 2);
		}

		String whereClause = "WHERE u.UserName = '" + username + "'";
		
		String tables = "userinformation u JOIN addressinformation a ON u.UserName = a.UserName";
		res = myDBConn2.doUpdate(tables, username, setClause, whereClause);

		return res;
	}

	/**
	 * Authenticates a user by verifying their username and password against the database.
	 *
	 * @param username the username of the user
	 * @param password the password of the user
	 * @return a ResultSet containing the user's username, assigned roles, and name if the authentication was successful, otherwise returns null
	 */
	public ResultSet authenticate(String username, String password)
	{
		
		//Declare function variables
		String fields, tables, whereClause, hashingVal;
		
		//Define the table where the selection is performed
		tables="userinformation, rolesforuser";
		//Define the list fields list to retrieve assigned roles to the user
		fields ="userinformation.username , rolesforuser.Id, userinformation.Name";
		hashingVal = hashingSha256(username + password);
		whereClause="userinformation.username = rolesforuser.UserName and userinformation.username='" + username +"' and passwordhash='" + hashingVal + "'";
		
		
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
	
	/**
	 * Retrieves the roles assigned to a given user for a given current and previous page.
	 *
	 * @param userName      The username of the user whose roles are to be retrieved.
	 * @param currentPage   The current page being accessed.
	 * @param previousPage  The previous page visited by the user.
	 * @return              A ResultSet containing the roles assigned to the user for the given pages.
	 */
	public boolean verifyUser(String userName, String currentPage, String previousPage)
	{
		
		//Declare function variables
		String fields, tables, whereClause, hashingVal, query;
		
		// Define the list of tables
		tables = "RolesForUser, Roles, roleForWebPage, webPages, UserInformation, webPageFlow";

		// Define the list of fields to retrieve assigned roles to the user
		fields = "UserInformation.UserName, Roles.roleID, UserInformation.Name";

		// Define the where clause to filter the results
		whereClause = "UserInformation.UserName = RolesForUser.UserName AND UserInformation.UserName = '" + userName + "' AND Roles.roleID = RolesForUser.roleID AND ";
		whereClause += "roleForWebPage.RoleID = Roles.roleID AND roleForWebPage.Page = webPages.Page AND webPages.Page = '" + currentPage + "' AND ";
		whereClause += "webPageFlow.previousPage = '" + previousPage + "' AND webPageFlow.currentPage = webPages.Page";
		
		query = "SELECT " + fields + " FROM " + tables + " WHERE " + whereClause;
		System.out.println("listing...");
		
		//Return the ResultSet containing all roles assigned to the user
		return myDBConn.doFlowSelect(query);
		
	}

	/**
	 * Adds a new user to the database.
	 * 
	 * @param username      The username of the new user.
	 * @param completeName  The complete name of the new user.
	 * @param userpass      The password of the new user.
	 * @param userTelephone The telephone number of the new user.
	 * @param dateOfBirth   The date of birth of the new user.
	 * @param gender        The gender of the new user.
	 * @param userEmail     The email address of the new user.
	 * @param street        The street of the new user.
	 * @param town          The town of the new user.
	 * @param state         The state of the new user.
	 * @param country       The country of the new user.
	 * @param degree        The degree of the new user.
	 * @param school        The school of the new user.
	 * 
	 * @return              true if the insertion was successful, false otherwise.
	 */
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
	
	/**
		hashingSha256 method
		Generates a hash value using the sha256 algorithm.
		@param plainText
		@return the hash string based on the plainText
	*/
	private String hashingSha256(String plainText)
	{
			String sha256hex = org.apache.commons.codec.digest.DigestUtils.sha256Hex(plainText); 
			return sha256hex;
	}
	
	/**
	 * Searches for users in the database based on various parameters.
	 * 
	 * @param town town of the user
	 * @param street the street of the user
	 * @param state the state of the user
	 * @param country the country of the user
	 * @param age age of the user
	 * @param gender gender of the user
	 * @param name name of the user
	 * @return a ResultSet containing the search results
	 */
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
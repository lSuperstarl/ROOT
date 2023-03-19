
//This class belongs to the ut.JAR.CPEN410 package
package ut.JAR.CPEN410;

//Import the java.sql package for managing the ResulSet objects
import java.sql.* ;

//Import hashing functions
import org.apache.commons.codec.*;

/******
	This class authenticate users using userName and passwords

*/
public class applicationDBAuthenticationGoodComplete{

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
	public ResultSet authenticate(String userName, String userPass)
	{
		
		//Declare function variables
		String fields, tables, whereClause, hashingVal;
		
		//Define the table where the selection is performed
		tables="usergood, roleuser";
		//Define the list fields list to retrieve assigned roles to the user
		fields ="usergood.userNam , roleuser.roleId, usergood.Name";
		hashingVal = hashingSha256(userName + userPass);
		whereClause="usergood.userName = roleuser.userName and usergood.userName='" +userName +"' and hashing='" + hashingVal + "'";
		
		
		System.out.println("listing...");
		
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
		tables="roleusergood, role, rolewebpagegood, menuElement,webpagegood ";
		//Define the list fields list to retrieve assigned roles to the user
		fields ="rolewebpagegood.pageURL, menuElement.title, webpagegood.pageTitle";
		whereClause=" roleusergood.roleID=role.roleID and role.roleID=rolewebpagegood.roleId and menuElement.menuID = webpagegood.menuID";
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
		whereClause+=" webpageprevious.previousPageURL='"+previousPage+"'";
		whereClause+=" webpage";
		
		
		System.out.println("listing...");
		
		//Return the ResultSet containing all roles assigned to the user
		return myDBConn.doSelect(fields, tables, whereClause);
		
		
	}
	
	public boolean addUser(String userName, String completeName, String userPass, String userTelephone)
	{
		boolean res;
		String table, values, hashingValue;
		hashingValue=hashingSha256(userName + userPass);
		table="usergood";
		values="'"+userName+"', '" +hashingValue+"', '"+ completeName + "', '" + userTelephone + "'";
		res=myDBConn.doInsert(table, values);
		System.out.println("Insertion result" + res);
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
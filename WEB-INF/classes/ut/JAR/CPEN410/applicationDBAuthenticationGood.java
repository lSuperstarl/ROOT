
//This class belongs to the ut.JAR.CPEN410 package
package ut.JAR.CPEN410;

//Import the java.sql package for managing the ResulSet objects
import java.sql.* ;

//Import hashing functions
import org.apache.commons.codec.*;

/******
	This class authenticate users using userName and passwords

*/
public class applicationDBAuthenticationGood{

	//myDBConn is an MySQLConnector object for accessing to the database
	private MySQLCompleteConnector myDBConn;
	
	/********
		Default constructor
		It creates a new MySQLConnector object and open a connection to the database
		@parameters:
		
	*/
	public applicationDBAuthenticationGood(){
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
		fields ="usergood.userName, roleuser.roleId";
		hashingVal = hashingSha256(userName + userPass);
		whereClause="usergood.userName = roleuser.userName and usergood.userName='" +userName +"' and hashing='" + hashingVal + "'";
		
		
		System.out.println("listing...");
		
		//Return the ResultSet containing all roles assigned to the user
		return myDBConn.doSelect(fields, tables, whereClause);
		
		
	}
	
	
	public ResultSet verifyUser(String userName, String currentPage, String previousPage)
	{
		
		//Declare function variables
		String fields, tables, whereClause, hashingVal;
		
		//Define the table where the selection is performed
		tables="roleuser, role, rolewebpage, webpage, usergood";
		//Define the list fields list to retrieve assigned roles to the user
		fields ="usergood.userName, roleuser.roleId";
		whereClause="usergood.userName = roleuser.userName and usergood.userName='" +userName +"' and role.roleId=roleuser.roleId and ";
		whereClause+=" rolewebpage.roleId=role.roleId and rolewebpage.pageURL=webpage.pageURL and webpage.pageURL='" +currentPage+"' and ";
		whereClause+=" webpage.previousPage='"+previousPage+"'";
		
		
		System.out.println("listing...");
		
		//Return the ResultSet containing all roles assigned to the user
		return myDBConn.doSelect(fields, tables, whereClause);
		
		
	}
	
	public boolean addUser(String userName, String completeName, String userPass, String userTelephone, String dateOfBirth, String gender, String userEmail, String address, String degree, String school)
	{
		boolean res;
		String table, values, hashingValue;
		hashingValue=hashingSha256(userName + userPass);
		table="usergood";
		values="'" + userName + "', '" + hashingValue + "', '" + completeName + "', '" + userTelephone + "', '" + dateOfBirth + "', '" + gender + "', '" + userEmail + "', '" + address + "', '" + degree + "', '" + school + "'";
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
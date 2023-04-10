// Import required java libraries
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import org.json.*;
import ut.JAR.CPEN410.*;
import java.sql.*;

/***
	This servlet generates a JSON object and sent it the a web browswer when required
*/


// Extend HttpServlet class
public class getDataBaseJson extends HttpServlet {
 
   //Users list (ArrayList)
   private ArrayList<userClass> users;
   
   //Servlet initialization
   public void init() throws ServletException {
      // Do required initialization
         }

	public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
	
		this.doPost(request, response);
	  }

	/* doGet Method
		Generates a JSON object containg a JSON Array list
	*/
   public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
	
		//Create an JSONObject containing a JSONArray
		JSONObject jsonResult = createFinalJSON();
      
      // Actual logic goes here.
      PrintWriter out = response.getWriter();
	  
	  // Set response content type
	  response.setContentType("application/json");
	  response.setCharacterEncoding("UTF-8");
      //Send the response
	  out.println(jsonResult.toString());
	     }

   public void destroy() {
      // do nothing.
   }
   
   /**
	Create a JSONObject containg a JSONArray
   **/
   public JSONObject createFinalJSON()
   {
		//Create the JSONObject	
	   JSONObject finalOutput = new JSONObject();
	   try{
		    //Create the JSONObject cointaing a JSONArray created using the createJSonArray method
			//name the JSONObject as "contact"
			finalOutput.put("departments", createJSonArray());
	   }catch(Exception e)
	   {
		   e.printStackTrace();
	   }
	   finally{
		   //Return the JSONObject cointaing the array
		   return finalOutput;
	   }
   }
   
   /**
	Create a JSONArray
	@param count: number of elements in the array
   */
   
   public JSONArray createJSonArray()
   {
	   
	   //Create the JSONArray
		   JSONArray jsonArray = new JSONArray();
	   
	   //Connect the the database
		applicationDBManager appDBMg = new applicationDBManager();
		int i=0;
		try{
			//query the database
		   ResultSet res= appDBMg.listAllDepartment();
			System.out.println("list all departments");
			while (res.next()){				//Add the new JSONObject to the JSONArray in location i
				
				jsonArray.put(i,createJSon(res));
				i++;
			}
			
		appDBMg.close();
		
	} catch(Exception e)
	   {
		   e.printStackTrace();
	   }
	   finally{
		   //Return the JSONArray
		   return jsonArray;
	   }
   }
   
   
   /*
		This method creates a JSONObject
   
   */ 
   public JSONObject createJSon(ResultSet res)
   {
	   //Create the JSONObject
	   JSONObject json = new JSONObject();
	   try{
			//Add the appropriate data to the object
			json.put("dept_name", res.getString(1));
			json.put("building", res.getString(2));
			json.put("image", "imagesjson/galaxy.jpg");

	
			
	} catch(Exception e)
	   {
		   e.printStackTrace();
	   }
	   finally{
		   //return the JSONObject
		   return json;
	   }
   }
}

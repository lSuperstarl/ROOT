<%@ page import="java.io.*, java.sql.*, ut.JAR.CPEN410.*, java.lang.*, java.util.*, org.apache.commons.fileupload.*, org.apache.commons.fileupload.disk.*, org.apache.commons.fileupload.servlet.*, org.apache.commons.io.FilenameUtils" %>
<%
    	//Check the authentication process

    if ((session.getAttribute("userName")==null) || (session.getAttribute("currentPage")==null)){
        session.setAttribute("currentPage", null);
        session.setAttribute("userName", null);
        response.sendRedirect("login.html");
    }

	else{

        String currentPage= "uploadProfilePicture.jsp";
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

                System.out.println("Printing Result Set: ");
                System.out.println(res);

				//Verify if the user has been authenticated
				if (res) {

                    
                    // Create the current page attribute
					session.setAttribute("currentPage", "uploadProfilePicture.jsp");
                    
                    String destination = "C:\\apache-tomcat-8.5.85\\webapps\\ROOT\\cpen410\\images\\regularusers\\";

                    String username = (String)session.getAttribute("userName");
                    DiskFileItemFactory factory = new DiskFileItemFactory();
                    factory.setSizeThreshold(1024);
                    factory.setRepository(new File(destination));
                    ServletFileUpload uploader = new ServletFileUpload(factory);

                    List items = uploader.parseRequest(request);
                    Iterator iterator = items.iterator();

                    while (iterator.hasNext()) {
                        FileItem item = (FileItem) iterator.next();
                        String fileName = item.getName();
                        String fileExtension = FilenameUtils.getExtension(fileName);
                        String baseName = FilenameUtils.getBaseName(fileName);
                        String timestamp = Long.toString(System.currentTimeMillis());
                        String newFileName = baseName + "-" + timestamp + "." + fileExtension;
                        File file = new File(destination, newFileName);
                        item.write(file);
                        String filename = '"' + newFileName + '"';
                        appDBAuth.setProfilePicture(filename, username);
                        out.write(file.getName() + " Uploaded");
                    }

                    response.sendRedirect("homePage.jsp");

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
					//Close the connection to the database
					appDBAuth.close();
				
				} catch(Exception e) {
					e.printStackTrace();
					response.sendRedirect("login.html");
				} finally {
					System.out.println("Finally");
				}
				
	}%>
// <%
//     String destination = "C:\\apache-tomcat-8.5.85\\webapps\\ROOT\\cpen410\\images\\regularusers\\";

//     applicationDBAuthenticationGoodComplete appDBAuth = new applicationDBAuthenticationGoodComplete();
//     String username = (String)session.getAttribute("userName");
//     DiskFileItemFactory factory = new DiskFileItemFactory();
//     factory.setSizeThreshold(1024);
//     factory.setRepository(new File(destination));
//     ServletFileUpload uploader = new ServletFileUpload(factory);
    
//     try {
//         List items = uploader.parseRequest(request);
//         Iterator iterator = items.iterator();

//         while (iterator.hasNext()) {
//             FileItem item = (FileItem) iterator.next();
//             String fileName = item.getName();
//             String fileExtension = FilenameUtils.getExtension(fileName);
//             String baseName = FilenameUtils.getBaseName(fileName);
//             String timestamp = Long.toString(System.currentTimeMillis());
//             String newFileName = baseName + "-" + timestamp + "." + fileExtension;
//             File file = new File(destination, newFileName);
//             item.write(file);
//             String filename = '"' + newFileName + '"';
//             appDBAuth.setProfilePicture(filename, username);
//             out.write(file.getName() + " Uploaded");
//         }
//         response.sendRedirect("homePage.jsp");

//     }

//     catch (FileUploadException e) {
//         out.write(e.getMessage());
//     }

//     catch (Exception e) {
//         out.write(e.getMessage());
//     }
// %>
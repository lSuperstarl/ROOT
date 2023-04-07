<%@ page import="java.io.*, ut.JAR.CPEN410.*, java.lang.*, java.util.*, org.apache.commons.fileupload.*, org.apache.commons.fileupload.disk.*, org.apache.commons.fileupload.servlet.*, org.apache.commons.io.FilenameUtils" %>

<%
    String destination = "C:\\apache-tomcat-8.5.85\\webapps\\ROOT\\cpen410\\images\\regularusers\\";

    applicationDBAuthenticationGoodComplete appDBAuth = new applicationDBAuthenticationGoodComplete();
    String username = (String)session.getAttribute("userName");
    DiskFileItemFactory factory = new DiskFileItemFactory();
    factory.setSizeThreshold(1024);
    factory.setRepository(new File(destination));
    ServletFileUpload uploader = new ServletFileUpload(factory);
    
    try {
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

    }

    catch (FileUploadException e) {
        out.write(e.getMessage());
    }

    catch (Exception e) {
        out.write(e.getMessage());
    }
%>
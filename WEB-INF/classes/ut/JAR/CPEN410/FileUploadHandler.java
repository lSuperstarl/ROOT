package ut.JAR.CPEN410;

import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet to handle File upload request from Client
 * @author: Javin Paul
 */
public class FileUploadHandler extends HttpServlet {
    // directory to store uploaded files
    private final String UPLOAD_DIRECTORY = "C:\\apache-tomcat-8.5.85\\webapps\\ROOT\\cpen410\\images";
  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // process only if it's multipart content
        if(ServletFileUpload.isMultipartContent(request)) {
            try {
                // parse the request to get the file items
                List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
              
                for(FileItem item : multiparts){
                    if(!item.isFormField()){
                        // get the name of the file
                        String name = new File(item.getName()).getName();
                        // save the file to the server
                        item.write(new File(UPLOAD_DIRECTORY + File.separator + name));
                    }
                }
                // File uploaded successfully
                request.setAttribute("message", "File Uploaded Successfully");
            } catch (Exception ex) {
                // handle exceptions during file upload
                request.setAttribute("message", "File Upload Failed due to " + ex);
            }          
        } else {
            // handle non-multipart content
            request.setAttribute("message", "Sorry, this Servlet only handles file upload requests");
        }
    }
}

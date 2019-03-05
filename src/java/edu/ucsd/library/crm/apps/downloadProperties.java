package edu.ucsd.library.crm.apps;
   
import java.io.IOException;  
import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.*;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import java.io.FileNotFoundException;  
import java.io.*;  
import java.util.*;
import java.util.Map;
import java.util.TreeMap;
import java.util.SortedMap;
import edu.ucsd.library.util.FileUtils;

public class downloadProperties extends HttpServlet {  
   
/** 
 * The doGet method of the servlet. <br> 
 * 
 * This method is called when a form has its tag value method equals to get. 
 *  
 * @param request the request send by the client to the server 
 * @param response the response send by the server to the client 
 * @throws ServletException if an error occurred 
 * @throws IOException if an error occurred 
 */  
public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, FileNotFoundException  {  
	String fileName = request.getParameter("fileName");
	response.setContentType("text/plain"); 
	response.setHeader("Content-Disposition","attachment;filename=" + fileName);
	ServletContext ctx = getServletContext();
	String marcFilesDir = ctx.getInitParameter("marcFilePath");	 
	ServletOutputStream os = response.getOutputStream();    

	if ((new File(marcFilesDir + fileName)).exists()) {
        BufferedReader is = new BufferedReader(new FileReader(marcFilesDir + fileName));

        int charRead = 0;
       
        while ((charRead = is.read()) != -1) {
            os.print((char)charRead);  
        }
   
        } else {
            os.print("Error: filename not found!");
        }	 
	    os.flush();  
	    os.close(); 
	}
}  



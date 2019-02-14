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
	 BufferedReader is = new BufferedReader(new FileReader(marcFilesDir + fileName));
	 is.readLine();
	 is.readLine();
	 is.readLine();
	 
	 String lineIn;
	 ServletOutputStream os = response.getOutputStream();  	 
	 Map<String,String> sortedMap = null;	 

    	 Map<String, String> propMap = new HashMap<String, String>();
	 while((lineIn = is.readLine()) != null)
	 {
         String[] temp = lineIn.split("=");
		 if(temp.length == 2)
			 propMap.put(temp[0],temp[1]);
		 else
			 propMap.put(temp[0], "");
	  }
	  sortedMap = new TreeMap(propMap);
		 
	  Iterator it = sortedMap.entrySet().iterator();
	  while(it.hasNext())
	  {
		  Map.Entry pairs = (Map.Entry)it.next();
		  if(pairs.getKey().toString().length() > 0) {
			  String temp2 = pairs.getKey().toString() + "=" + pairs.getValue() + "\n";
		 	  os.write(temp2.getBytes());
		   }
	  }
	 
	  os.flush();  
	  os.close(); 
	}
}  



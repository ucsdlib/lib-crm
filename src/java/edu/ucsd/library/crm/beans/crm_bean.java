package edu.ucsd.library.crm.beans;
/*
 * crm_bean.java
 *
 * Created on June 26, 2002, 9:10 AM
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.Vector;
import java.util.HashMap; 
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;
import java.util.SortedMap;


import javax.servlet.http.HttpServletRequest;

import edu.ucsd.library.util.FileUtils;
import edu.ucsd.library.util.TextUtils;

/**
 *
 * @author  Joseph Jesena
 */
public class crm_bean {
    
    /** Creates new crm_bean */
    public crm_bean() {
    	//incquery.setMarcFilesDir(marcFilesDir);
    }
    
    /** Method to retreive the hash value of a password. Use this to generate
     * the initial password of the first user
     * @param args  */
    public static void main(String[] args) {
        crm_bean my = new crm_bean();
        System.out.println("Hash: " + my.getSecureHash(args[0]));
    }
    
    /**
     * Method to get the hash value of an input String
     * @param pass The String to compute the hash from
     * @return String - The resulting hash value
     */
    public String getSecureHash(String pass) {
        byte[] buf= new byte[pass.length()];
        buf = pass.getBytes();
        
        MessageDigest algorithm = null;
        try {
            algorithm = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        algorithm.reset();
        algorithm.update(buf);
        byte[] digest1 = algorithm.digest();
        
        char[] myChar =
        edu.ucsd.library.util.math.HexDumper.formatBytes(digest1);
        
        String myStr = new String(myChar);
        myStr = TextUtils.strReplace(myStr, " ", "");
        
        return myStr;
    }

    /**
     * @return  */
    public String getContextDir() {
        return contextDir;
    }
    
    /**
     * @param str  */
    public void setContextDir(String str) {
        contextDir = str;
    }
    
    /**
     * @return  */
    public String getContextUrl() {
        return contextUrl;
    }
    
    /**
     * @param str  */
    public void setContextUrl(String str) {
        contextUrl = str;
    }
    
    /**
     * @return  */
    public String getMarcFilesDir() {
        return marcFilesDir;
    }
    
    /**
     * @param str  */
    public void setMarcFilesDir(String str) {
        marcFilesDir = str;
        //incquery.setMarcFilesDir(marcFilesDir);
        //fullquery.setMarcFilesDir(marcFilesDir);
    }
    
    /**
     * Delete the given MARC file
     * @param filen The file to delete
     * @return boolean - Was the file deleted?
     */
    public boolean deleteMarcFile(String filen) {
        //String marcFilesDir =  contextDir + "WEB-INF" + File.separator +
        //"marc_files" + File.separator;
        File myF = new File( marcFilesDir + filen);
        return (myF.delete());
    }

    /**
     * Method to get CSV file names in HTML
     * @return String - the list of MARC files in HTML
     */
    public String getCsvFileNames() {
        
        try {
            FileUtils.confirmDir(marcFilesDir);
        } catch (IOException ioe) {
            //just ignore it for now....
        }
        
        
        //String marcFileUrl = contextUrl.substring(0, contextUrl.length()-4) + "getcsvfile";
        String marcFileUrl = contextUrl + "getcsvfile";
        
        File myF = new File( marcFilesDir);
        String[] myFiles = myF.list();
        
        StringBuffer retValue = new StringBuffer("");
        
        // format time like: July 04 10:48:39 PDT 2002
        Format formatter;
        formatter = new SimpleDateFormat("MMM dd kk:mm:ss yyyy");
        
        for (int i=0; i < myFiles.length; i++) {
            File testF = new File(marcFilesDir + myFiles[i]);
            
            if ((testF.isFile()) && (testF.getName().endsWith(".csv")) ) {
                retValue.append("<tr>");
                
                java.util.Date date = new java.util.Date(testF.lastModified());
                String dateString = formatter.format(date);
                
                retValue.append("<td>" + "<a href=\"" + marcFileUrl + "?file=" + myFiles[i] + "\"> " + myFiles[i] + "</a></td>");
                retValue.append("<td>" + dateString + "</td>");
                retValue.append("<td>" + (testF.length() / 1024) + "k</td>");
                retValue.append("<td> <input type=\"checkbox\" name=\"remove_" + myFiles[i] + "\"> </td>");
                retValue.append("</tr>");
            }
        }
        
        return retValue.toString();
    }
    
    /**
     * @return  */
    public String getCurrentUser() {
        return currentUser;
    }
    
    /**
     * @return  */
    public String getPathToProperties() {
        return pathToProperties;
    }
    
    /**
     * @param in  */
    public void setPathToProperties(String in) {
        pathToProperties = in;
    }

	/**
	 * Method to do an incremental database query for employees
	 */
	public void doAllEmployee() {
		String[] tmp = new String[2];
		
		String propsDir = pathToProperties.substring(0, pathToProperties.lastIndexOf(File.separator)+1);
		tmp[0] = marcFilesDir;
		tmp[1] = marcFilesDir;
		edu.ucsd.library.crm.apps.create_employee_file.main(tmp);
	}
	
	/**
	 * Method to change the properties file
	 */
	public int setPropertiesFile(String file, String name, String newValue) throws IOException
	{
		 Properties myProp = null;
		 Object o = null;
		 String link = "";
	        try {
	        	link = pathToProperties.substring(0,pathToProperties.length()-22);
	            myProp = FileUtils.loadProperties(link + file);
	        } catch (IOException ioe) {
	        	
	        }  
	        o = myProp.setProperty(name, newValue);
	        String cmt;
	        
	        if(file.equals("employee_types.properties"))
	       		cmt ="#Staff Group\n";
	       	else 
	       		cmt = "#\n#Wed Apr 09 15:47:59 PDT 2008\n";
	        
	        try {
	            FileOutputStream fos = new FileOutputStream(link + file);
	            myProp.store(fos, cmt);
	            fos.close();
	        } catch (FileNotFoundException fnfe) {
	            return 2;
	        } catch (IOException ioe) {
	            return 3;
	        }
        if(o == null)
        	return 0;
        else
        	return 1;
	}
	
	public int delProperties(String file, String name) throws IOException
	{
		Properties myProp = null;
		 Object o = null;
		 String link = "";
	        try {
	        	link = pathToProperties.substring(0,pathToProperties.length()-22);
	            myProp = FileUtils.loadProperties(link + file);
	        } catch (IOException ioe) {
	        	
	        }  
	        o = myProp.remove(name);
	        String cmt;
	        
	        if(file.equals("employee_types.properties"))
	       		cmt ="#Staff Group\n";
	       	else 
	       		cmt = "#\n#Wed Apr 09 15:47:59 PDT 2008\n";
	        
	        try {
	            FileOutputStream fos = new FileOutputStream(link + file);
	            myProp.store(fos, cmt);
	            fos.close();
	        } catch (FileNotFoundException fnfe) {
	            return 2;
	        } catch (IOException ioe) {
	            return 3;
	        }
       if(o == null)
       	return 0;
       else
       	return 1;
	}
	
	
	/**
	 * Method to get the whole set from the properties file
	 */
	public Map getPropertiesSet(String file)  throws IOException
	{
		 
		Properties props = null;
		Object o = null;
		String link = "";
        try {
        	link = pathToProperties.substring(0,pathToProperties.length()-22);
        	props = FileUtils.loadProperties(link + file);
        } catch (IOException ioe) {
        	
        } 
        Map<String,String> sortedMap = null;
        	Map<Integer, String> propMap = new HashMap<Integer, String>();
  	    Enumeration e = props.propertyNames();
  	    for (; e.hasMoreElements(); ) {
  	        String propName = (String)e.nextElement();
  	        int propKey = Integer.parseInt(propName);
  	        String propValue = (String)props.get(propName);
  	        if(propValue.length() < 5)
  	        propMap.put(propKey, propValue);
  	    }    
  	    sortedMap = new TreeMap(propMap);
       return sortedMap;
	}
	
	/**
	 * Method to search for the existed key
	 */
	public boolean hasPropertiesKey(String file, String key)throws IOException
	{
		Properties props = null;
		Object o = null;
		String link = "";
        try {
        	link = pathToProperties.substring(0,pathToProperties.length()-22);
        	props = FileUtils.loadProperties(link + file);
        } catch (IOException ioe) {
        	
        } 
        if(props.getProperty(key) == null)
        	return false;
        return true;
	}
	
    private String contextDir;
    private String contextUrl;
    private String marcFilesDir;
    private String currentUser;
    private String pathToProperties;    
}

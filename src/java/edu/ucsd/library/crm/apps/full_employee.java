package edu.ucsd.library.crm.apps;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.NamingException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import edu.ucsd.library.util.FileUtils;
import edu.ucsd.library.util.sql.ConnectionManager;

/**
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class full_employee {

	/**
	 * @param args the command line arguments
	 * args[0] = path to org name properties file
	 * args[1] = file to write output to
	 * args[2] = file to read from
	 */
	public static void main(String args[]) {

		if ((args == null) || (args.length < 4)) {
			System.out.println(
				"\nSyntax: java full_employee [org name properties file] [fileToWrite] [fileToRead]");
		} else {
			grabData(args[0], args[1], args[2]);
		}
	}
	
	private static void writeFile(StringBuffer in, String file) {
		if (in.toString().length() > 0) {
			try {
				FileUtils.stringToFile(in.toString(), outputLocation + file);
			} catch (IOException ioe) {
			}
		}
	}

	/**
	 * Write data out to the file
	 * @param propsOrgs Path to affilications properties file
     * @param fileToWrite Path to the file to write results to
	 * @param fileToRead Path to the file to read results from
	 */
	public static void grabData(String propsOrgs, String fileToWrite, String fileToRead) {

		outputLocation = fileToWrite.substring(0, fileToWrite.lastIndexOf(File.separator) + 1);
		
		PrintWriter pw = null;

		//--- Create the file stream here to output to file
		try {
			
			pw =
				new PrintWriter(
					new BufferedOutputStream(
						new FileOutputStream(fileToWrite)));

			getRawData(propsOrgs, pw, fileToRead);

			if (pw != null)
				pw.close();


		} catch (IOException ioe) {
			System.out.println(ioe);
		}
	}



	/**
	 * Method to retreive data from database and output to raw file.
	 * @param propsOrgs Path to orgs name properties file
	 * @param pw PrintWriter
	 * @param fileToRead Path to the file to write results to
	 */
	public static void getRawData(String propsOrgs, PrintWriter pw, String fileToRead) {
	    StringBuffer writeOut = new StringBuffer();
        String lineIn = "";;
        BufferedReader in = null;
        Map tmpMap = new HashMap();
        String[] strArray = null;
        try {
            tmpMap = loadMapping(propsOrgs);
            in = new BufferedReader(new FileReader(fileToRead));
            while (((lineIn = in.readLine()) != null)
                && !(lineIn.trim().equals(""))) {
                lineIn = lineIn.trim();
                strArray = lineIn.split("\t");
                for (int v = 0; v < strArray.length; v++) {
                    if(tmpMap.containsKey(strArray[v].trim())) {
                        pw.write((String)tmpMap.get(strArray[v].trim()));
                    } else {
                        if(!strArray[v].trim().equals("BEHAVIOR & EVOLUTION") && !strArray[v].trim().equals("MSCDRON DEPT OF RAD ONC") && !strArray[v].trim().equals("HEALTH & SAFETY")) {
                           pw.write(strArray[v].trim());
                        }
                    }
                    if(v < strArray.length-1) {
                        pw.write("\t");
                    } else {
                        pw.write("\n");
                    }                     
                }
            }
        } catch (IOException ioe) {
            System.out.println("Error loading properties file!");
            return;
        }
	    try {
			pw.write(writeOut.toString());			
		} catch (Exception e) {
		    System.err.println("Exception in transforming csv file: " + e);
        } 
	}
	
	public static Map loadMapping(String propsOrgs) {
        String lineIn = "";;
        BufferedReader in = null;
        Map tmpMap = new HashMap();
        String[] strArray = null;
        try {
            in = new BufferedReader(new FileReader(propsOrgs));
            while (((lineIn = in.readLine()) != null)
                && !(lineIn.trim().equals(""))) {
                lineIn = lineIn.trim();
                strArray = lineIn.split("=");
                if (strArray.length > 2) {
                    System.out.println("problem mapping:"+lineIn);
                }
                if(!tmpMap.containsKey(strArray[0].trim())) {
                    tmpMap.put(strArray[0].trim(), strArray[1].trim());
                } 
            }
        } catch (IOException ioe) {
            System.out.println("Error loading properties file!");
        }
        return tmpMap;
    }
	private static String outputLocation;
}








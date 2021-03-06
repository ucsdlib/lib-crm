/*
 * Created on Oct 1, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package edu.ucsd.library.crm.apps;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

/**
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class makecsv_employee {

	public static void makeCsv(String propsFolder, String fileToRead, String fileToWrite) {

		BufferedReader in = null;
		PrintWriter pw = null;

		try {
			in = new BufferedReader(new FileReader(fileToRead));
			pw =
				new PrintWriter(
					new BufferedOutputStream(
						new FileOutputStream(fileToWrite)));
			String lineIn = "";
			Map tmpMap = new HashMap();
			String[] strArray = null;
			Vector tmpPhone = null, tmpTitle = null, tmpEmail = null, tmpDep = null, tmpMailCode = null, firstName = null, lastName = null; 
	        String key = null, value = null;
	        String[] dataArray = null;
	        Map apptTypeMap = new HashMap();
            boolean hasApptType = false;
            if (fileToRead.contains("transform")) {
                hasApptType = true;
                apptTypeMap = full_employee.loadMapping(propsFolder + File.separator + "appointment_mapping.properties");
            }	        
			writeToFileHeader(pw, hasApptType);
			while (((lineIn = in.readLine()) != null)
				&& !(lineIn.trim().equals(""))) {
				lineIn = lineIn.trim();
				strArray = lineIn.split("\t");
				if(!tmpMap.containsKey(strArray[0].trim())) {
					tmpMap.put(strArray[0].trim(), lineIn.trim());
				} else {
					tmpMap.put(strArray[0].trim(), tmpMap.get(strArray[0].trim())+" # "+lineIn.trim());
				}
			}    
			 for (Iterator i = tmpMap.keySet().iterator(); i.hasNext();) {
                 key = (String) i.next();
                 value = tmpMap.get(key).toString();
			        if (value != null) {
			        	  strArray = value.split(" # ");
			        	  tmpMailCode = new Vector();
			        	  tmpTitle = new Vector();
			        	  tmpPhone = new Vector();
			          tmpEmail = new Vector();
			          tmpDep = new Vector();
			          firstName = new Vector();
			          lastName = new Vector();	
			          int arrayLength = strArray.length;
			          if (arrayLength > 5) {
			        	    arrayLength = 5;
			          }
			        	    
			  			for (int j = 0; j < arrayLength; j++) {
			  				dataArray = strArray[j].split("\t");
		  					if(!tmpTitle.contains(dataArray[3].trim())) {	
				  				lastName.add(dataArray[1].trim());
				  				firstName.add(dataArray[2].trim());	  					
				  				tmpTitle.add(dataArray[3].trim());
				  				tmpPhone.add(dataArray[4].trim());
				  				tmpEmail.add(dataArray[5].trim());
				  				tmpDep.add(dataArray[6].trim());
				  				if(dataArray.length > 7) {
						  		  tmpMailCode.add(dataArray[7].trim());
				  				}
				  				else {
				  				  tmpMailCode.add("(null)");
		  					    }
			  			    }
			  			}

			  			pw.write(lastName.get(0)+","+firstName.get(0));
			  			if (hasApptType == true) {
			  			    writeApptType(pw, apptTypeMap, tmpTitle);
			  			}
			  			writeDataToFile(pw, tmpTitle);
			  			writeDataToFile(pw, tmpPhone);
			  			writeDataToFile(pw, tmpEmail);
			  			writeDataToFile(pw, tmpDep);
			  			writeDataToFile(pw, tmpMailCode);
			  			pw.write("\n");
			  			
			        }
			    }
			pw.close();
		} catch (IOException ioe) {
			System.out.println(ioe);
		} catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        } finally {
			try {
				pw.close();
			} catch (Exception e) {
			}
		}

	}

    private static void writeApptType(PrintWriter bw, Map apptTypeMap, Vector titleVec)  throws IOException{
        String apptType = " ";
        if (titleVec.size() > 0) {
            apptType = titleVec.get(0).toString().trim();
            if(apptTypeMap.containsKey(apptType)) {
                apptType = (String)apptTypeMap.get(apptType);
            } else {
                apptType = " ";
            }
        } 
        bw.write(","+apptType);	        
    }
	   
	private static void writeDataToFile(PrintWriter bw, Vector input)  throws IOException{
		bw.write(","+input.get(0).toString().replace("(null)", " "));
	}
	
	private static void writeToFileHeader(PrintWriter bw, boolean hasApptType) {
		bw.write("EMP_LAST_NAME,EMP_FIRST_NAME,");
		if (hasApptType == true) {
		    bw.write("EMP_TYPE,");
		}
		bw.write("APP_TITLE_NAME_1,PHONE_1,EMAIL_1,APP_DEPARTMENT_NAME_1,MAIL_CODE_1\n");
	}
	
	/**
	* @param args the command line arguments
	* args[0] = folder of crm properties file
	* args[1] = fileToRead
	* args[2] = fileToWrite
	*/
	public static void main(String args[]) {

		if ((args == null) || (args.length < 3)) {
			System.out.println(
				"\nSyntax: java makecsv_employee [properties folder] [fileToRead] [fileToWrite]");
		} else {
			makeCsv(args[0], args[1], args[2]);
		}
	}

}

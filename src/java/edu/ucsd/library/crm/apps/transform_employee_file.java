package edu.ucsd.library.crm.apps;

/*
 * 
 * transform_employee_file.java
 *
 */

import java.io.File;
import java.text.Format;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class transform_employee_file {

	/** Creates new create_employee_file */
	public transform_employee_file() {
	}

	/**
	 * Method to generate the filenames of the resulting CSV file
	 * @return String - The filename
	 */
	public static String createCsvFileName() {

		Format formatter;
		formatter = new SimpleDateFormat("yyyy-MMM-dd");
		String dateString = formatter.format(new java.util.Date());
		return ("inc-employees-" + dateString + ".csv");
	}

	/**
	 * Method to generate the filenames of the resulting CSV file
	 * @return String - The filename
	 */
	public static String createFullCsvFileName() {

		Format formatter;
		formatter = new SimpleDateFormat("yyyy-MMM-dd");
		String dateString = formatter.format(new java.util.Date());
		return ("full-employees-" + dateString + ".csv");
	}
	
	/**
	* @param args the command line arguments
	* args[0] = destination directory
	* args[1] = properties directory
	*/
	public static void main(String args[]) {

		if ((args == null) || (args.length < 2)) {
			System.out.println(
				"\nSyntax: java transform_employee_file [destination directory] [properties dir]");
		} else {
			String org_name_mapping = args[1] + File.separator + "org_name_mapping.properties";
			try {
				File tmp = new File(args[0] + "all_transform_employee.txt");
				DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		        String modifiedDatePart = null;
		        if(tmp.exists())
		            modifiedDatePart = formatter.format(tmp.lastModified());
		        
		        Calendar c =  Calendar.getInstance();
		        String todayDatePart = formatter.format(c.getTime());

                full_employee.grabData(org_name_mapping, args[0] + File.separator + "all_transform_employee.txt", 
                    args[0] + File.separator + "all_active_employee.txt");
				makecsv_employee.makeCsv(
							args[1],
							args[0] + File.separator + "all_transform_employee.txt",
							args[0] + File.separator + createFullCsvFileName());
				
				//Create incremental employees file
				
				full_employee.grabData(org_name_mapping, args[0] + File.separator + "inc_transform_employee.txt", 
                    args[0] + File.separator + "inc_all_employee.txt");
				makecsv_employee.makeCsv(
                    args[1],
                    args[0] + File.separator + "inc_transform_employee.txt",
                    args[0] + File.separator + createCsvFileName());
			} catch (Exception e) {
			}					
		}
		System.out.println("end");
	}

}
